package org.gfa.avustribesbackend.services.Email;

import org.gfa.avustribesbackend.models.Player;
import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {
  private final JavaMailSender javaMailSender;
  private final PlayerRepository playerRepository;

  @Autowired
  public EmailVerificationServiceImpl(
      JavaMailSender javaMailSender, PlayerRepository playerRepository) {
    this.javaMailSender = javaMailSender;
    this.playerRepository = playerRepository;
  }

  @Override
  public void sendVerificationEmail(String token) {
    Player player = playerRepository.findByVerificationToken(token);

    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(player.getEmail());
    message.setSubject("Registration Confirmation");
    message.setText("Hello new player! Click this link: http://localhost:8080/email/verify/" + player.getVerificationToken());

    javaMailSender.send(message);
  }

  @Override
  public boolean verifyEmail(String token) {
    Player player = playerRepository.findByVerificationToken(token);

    boolean tokenValid = false;
    Date expirationDate = player.getVerificationTokenExpiresAt();
    Date currentDate = new Date(System.currentTimeMillis());
    if (currentDate.before(expirationDate)) {
      tokenValid = true;
    }

    if (tokenValid) {
      player.setVerifiedAt(currentDate);
      player.setVerified(true);
      playerRepository.save(player);
      return true;
    } else {
      return false;
    }
  }
}
