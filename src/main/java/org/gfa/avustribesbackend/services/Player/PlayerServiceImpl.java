package org.gfa.avustribesbackend.services.Player;

import org.gfa.avustribesbackend.dtos.PlayerInfoDTO;
import org.gfa.avustribesbackend.dtos.PlayerRegistrationBody;
import org.gfa.avustribesbackend.models.RegistrationError;
import org.gfa.avustribesbackend.models.Player;
import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.gfa.avustribesbackend.services.Email.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import io.github.cdimascio.dotenv.Dotenv;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
public class PlayerServiceImpl implements PlayerService {
  Dotenv dotenv = Dotenv.configure().load();
  String verifyEmailEnabled = dotenv.get("VERIFY_EMAIL_ENABLED");

  private final PlayerRepository playerRepository;
  private final EmailVerificationService emailVerificationService;

  @Autowired
  public PlayerServiceImpl(
      PlayerRepository playerRepository, EmailVerificationService emailVerificationService) {
    this.playerRepository = playerRepository;
    this.emailVerificationService = emailVerificationService;
  }

  @Override
  public ResponseEntity<Object> registerPlayer(PlayerRegistrationBody request) {
    RegistrationError error = new RegistrationError();
    if (request.getUsername() == null) {
      error.setError("Username is required");
      return ResponseEntity.status(400).body(error);
    } else if (request.getPassword() == null) {
      error.setError("Password is required");
      return ResponseEntity.status(400).body(error);
    } else if (request.getEmail() == null) {
      error.setError("Email is required");
      return ResponseEntity.status(400).body(error);
    } else if (playerRepository.existsByUserName(request.getUsername())) {
      return ResponseEntity.status(409).body("Username is already taken");
    }
    // extra one:) ->
    else if (playerRepository.existsByEmailIgnoreCase(request.getEmail())) {
      return ResponseEntity.status(400).body("Email is already taken");
    }
    // <-
    else if (request.getUsername().length() < 4) {
      error.setError("Username must be at least 4 characters long");
      return ResponseEntity.status(400).body(error);
    } else if (request.getPassword().length() < 8) {
      error.setError("Password must be at least 8 characters long");
      return ResponseEntity.status(400).body(error);
    } else if (!validateEmail(request.getEmail())) {
      error.setError("Invalid email");
      return ResponseEntity.status(400).body(error);
    }
    Player player =
        new Player(
            request.getUsername(), request.getEmail(), request.getPassword(), verificationToken());
    if (player == null) {
      error.setError("Unknown error");
      return ResponseEntity.status(400).body(error);
    }
    playerRepository.save(player);
    if (verifyEmailEnabled.equals("true")) {
      String token = player.getVerificationToken();
      emailVerificationService.sendVerificationEmail(token);
    } else {
      Date date = new Date(System.currentTimeMillis());
      player.setVerifiedAt(date);
      player.setIsVerified(true);
      playerRepository.save(player);
    }
    return ResponseEntity.ok("successful creation");
  }

  @Override
  public boolean validateEmail(String email) {
    boolean isValid = false;
    try {
      InternetAddress internetAddress = new InternetAddress(email);
      internetAddress.validate();
      isValid = true;
    } catch (AddressException e) {
      e.printStackTrace();
    }
    return isValid;
  }

  @Override
  public String verificationToken() {
    SecureRandom secureRandom = new SecureRandom();
    byte[] tokenBytes = new byte[32];
    secureRandom.nextBytes(tokenBytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
  }

  @Override
  public PlayerInfoDTO createPlayerInfoDTO(Player player) {
    PlayerInfoDTO dto = null;
    if (player.getIsVerified()) {
      dto = new PlayerInfoDTO(
              player.getId(), player.getUserName(), player.getIsVerified(), player.getVerifiedAt());
    } else {
      dto = new PlayerInfoDTO(player.getId(), player.getUserName(), player.getIsVerified(), null);
    }
    return dto;
   }

  @Override
  public List<PlayerInfoDTO> listPlayerInfoDTO() {
    List<Player> allPlayers = playerRepository.findAll();
    List<PlayerInfoDTO> dtoList = new ArrayList<>();
    for (Player player : allPlayers){
        PlayerInfoDTO dto = createPlayerInfoDTO(player);
        dtoList.add(dto);
    }
    return dtoList;
  }

  @Override
  public PlayerInfoDTO findPlayerDTOById(Long id) {
    Optional<Player> playerOptional = playerRepository.findById(id);
    if (playerOptional.isPresent()){
      PlayerInfoDTO dto = createPlayerInfoDTO(playerOptional.get());
      return dto;
    } else {
      return null;
    }
  }

  @Override
  public boolean checkId(Long id) {
      return findPlayerDTOById(id) != null;
  }
}
