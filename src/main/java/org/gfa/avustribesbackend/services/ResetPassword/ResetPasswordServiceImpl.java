package org.gfa.avustribesbackend.services.ResetPassword;

import org.gfa.avustribesbackend.dtos.EmailRequestDTO;
import org.gfa.avustribesbackend.dtos.PasswordRequestDTO;
import org.gfa.avustribesbackend.dtos.TokenRequestDTO;
import org.gfa.avustribesbackend.exceptions.CredentialException;
import org.gfa.avustribesbackend.exceptions.VerificationException;
import org.gfa.avustribesbackend.models.Player;
import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.gfa.avustribesbackend.services.Player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {

  private final JavaMailSender javaMailSender;
  private final PlayerRepository playerRepository;
  private final PlayerService playerService;

  @Autowired
  public ResetPasswordServiceImpl(
      JavaMailSender javaMailSender,
      PlayerRepository playerRepository,
      PlayerService playerService) {
    this.javaMailSender = javaMailSender;
    this.playerRepository = playerRepository;
    this.playerService = playerService;
  }

  @Override
  public ResponseEntity<Object> sendResetPasswordEmail(EmailRequestDTO email) {
    if (email == null || email.getEmail() == null || email.getEmail().isEmpty() || !playerRepository.existsByEmailIgnoreCase(email.getEmail())) {
      throw new CredentialException("Invalid email!");
    }

    Player player = playerRepository.findByEmailIgnoreCase(email.getEmail());

    if (player.getVerifiedAt() == null) {
      throw new VerificationException("Unverified email!");
    }

    player.setForgottenPasswordToken(playerService.verificationToken());
    player.setForgottenPasswordTokenExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60));

    playerRepository.save(player);

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(player.getEmail());
    message.setSubject("Password reset");
    message.setText("Hello " + player.getUserName() + ". If you want to reset your password please click on this link: http://localhost:8080/reset-password/" + player.getForgottenPasswordToken());

    javaMailSender.send(message);

    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Object> resetPassword(TokenRequestDTO token, PasswordRequestDTO newPassword) {
    if (newPassword == null || newPassword.getPassword() == null || newPassword.getPassword().isEmpty()) {
      throw new CredentialException("Password is required");
    }
    if (token == null || token.getToken() == null || token.getToken().isEmpty() || !playerRepository.existsByForgottenPasswordToken(token.getToken())) {
      throw new VerificationException("Invalid token");
    }
    if (newPassword.getPassword().length() < 8) {
      throw new CredentialException("Password must be at least 8 characters long");
    }

    Player player = playerRepository.findByForgottenPasswordToken(token.getToken());

    if (player.getForgottenPasswordTokenExpiresAt().before(new Date(System.currentTimeMillis()))) {
      throw new VerificationException("Expired token");
    }

    player.setPassword(newPassword.getPassword());
    playerRepository.save(player);

    return ResponseEntity.ok().build();
  }
}

