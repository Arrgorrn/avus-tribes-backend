package org.gfa.avustribesbackend.services.Player;

import org.gfa.avustribesbackend.dtos.PlayerRegistrationBody;
import org.gfa.avustribesbackend.exceptions.AlreadyExistsException;
import org.gfa.avustribesbackend.exceptions.CreationException;
import org.gfa.avustribesbackend.exceptions.CredentialException;
import org.gfa.avustribesbackend.models.RegistrationError;
import org.gfa.avustribesbackend.models.Player;
import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.gfa.avustribesbackend.services.Email.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
public class PlayerServiceImpl implements PlayerService {

  private final PlayerRepository playerRepository;
  private final EmailVerificationService emailVerificationService;

  @Autowired
  public PlayerServiceImpl(
      PlayerRepository playerRepository,
      EmailVerificationService emailVerificationService) {
    this.playerRepository = playerRepository;
    this.emailVerificationService = emailVerificationService;
  }

  @Override
  public ResponseEntity<Object> registerPlayer(PlayerRegistrationBody request) {
    if (request.getUsername() == null || request.getUsername().isEmpty()) {
      throw new CredentialException("Username is required");
    } else if (request.getPassword() == null || request.getPassword().isEmpty()) {
      throw new CredentialException("Password is required");
    } else if (request.getEmail() == null || request.getEmail().isEmpty()) {
      throw new CredentialException("Email is required");
    } else if (playerRepository.existsByUserName(request.getUsername())) {
      throw new AlreadyExistsException("Username is already taken");
    } else if (playerRepository.existsByEmailIgnoreCase(request.getEmail())) {
      throw new AlreadyExistsException("Email is already taken");
    } else if (request.getUsername().length() < 4) {
      throw new CredentialException("Username must be at least 4 characters long");
    } else if (request.getPassword().length() < 8) {
      throw new CredentialException("Password must be at least 8 characters long");
    } else if (!validateEmail(request.getEmail())) {
      throw new CredentialException("Invalid email");
    }

    Player player = new Player(
        request.getUsername(),
        request.getEmail(),
        request.getPassword(),
        verificationToken());

    if (player == null) {
      throw new CreationException("Unknown error");
    }

    playerRepository.save(player);

    String token = player.getVerificationToken();
    emailVerificationService.sendVerificationEmail(token);

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

//  @Override
//  public String encodePassword(String password) {
//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//    return encoder.encode(password);
//  }
}
