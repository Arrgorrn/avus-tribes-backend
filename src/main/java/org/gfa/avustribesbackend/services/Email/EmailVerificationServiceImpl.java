package org.gfa.avustribesbackend.services.Email;

import org.gfa.avustribesbackend.models.Player;
import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    String user = player.getUserName();
    String verificationLink = "http://localhost:8080/email/verify/" + token;

    String htmlTemplate = readHtmlTemplateFromFile("src/main/resources/mailTemplate.html");
    htmlTemplate = htmlTemplate.replace("${user}", user);
    htmlTemplate = htmlTemplate.replace("${verificationLink}", verificationLink);

    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
      helper.setTo(player.getEmail());
      helper.setFrom("avustribesbackend@gmail.com");
      helper.setSubject("Registration Confirmation");
      helper.setText(htmlTemplate, true);

      javaMailSender.send(mimeMessage);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean verifyEmail(String token) {
    Player player = playerRepository.findByVerificationToken(token);

    // check if token valid
    boolean tokenValid = false;
    Date expirationDate = player.getVerificationTokenExpiresAt();
    Date currentDate = new Date(System.currentTimeMillis());
    if (currentDate.before(expirationDate)) {
      tokenValid = true;
    }

    // verify player
    if (tokenValid) {
      player.setVerifiedAt(currentDate);
      playerRepository.save(player);
      return true;
    } else {
      return false;
    }
  }

  private String readHtmlTemplateFromFile(String filePath) {
    try {
      Path path = Paths.get(filePath);
      byte[] encoded = Files.readAllBytes(path);
      return new String(encoded, "UTF-8");
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    }
  }
}
