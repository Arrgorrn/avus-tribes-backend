package org.gfa.avustribesbackend.services.Email;

import io.github.cdimascio.dotenv.Dotenv;
import org.gfa.avustribesbackend.exceptions.EmailException;
import org.gfa.avustribesbackend.exceptions.VerificationException;
import org.gfa.avustribesbackend.models.Player;
import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
  private final Dotenv dotenv = Dotenv.configure().load();
  private final String url = dotenv.get("VERIFICATION_EMAIL_URL");
  private final String sender = dotenv.get("VERIFICATION_EMAIL_SENDER");
  private final String subject = dotenv.get("VERIFICATION_EMAIL_SUBJECT");
  private final String templatePath = dotenv.get("VERIFICATION_EMAIL_TEMPLATE_FILEPATH");

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
    String verificationLink = url + token;

    String htmlTemplate = readHtmlTemplateFromFile(templatePath);
    htmlTemplate = htmlTemplate.replace("${user}", user);
    htmlTemplate = htmlTemplate.replace("${verificationLink}", verificationLink);

    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
      helper.setTo(player.getEmail());
      helper.setFrom(sender);
      helper.setSubject(subject);
      helper.setText(htmlTemplate, true);

      javaMailSender.send(mimeMessage);
    } catch (MessagingException e) {
      throw new EmailException("Unable to send email, please try again");
    }
  }

  @Override
  public boolean isVerified(Player player) {
    return player.getIsVerified();
  }

  @Override
  public boolean isVerified(String token) {
    Player player = playerRepository.findByVerificationToken(token);
    if (player == null) {
      throw new EmailException("Player not found");
    }
    return player.getIsVerified();
  }

  @Override
  public boolean verifyEmail(String token) {
    Player player = playerRepository.findByVerificationToken(token);
    if (!isVerified(player)) {

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
        player.setIsVerified(true);
        playerRepository.save(player);
        return true;
      } else {
        return false;
      }
    } else {
      throw new VerificationException("User already verified");
    }
  }

  private String readHtmlTemplateFromFile(String filePath) {
    try {
      Path path = Paths.get(filePath);
      byte[] encoded = Files.readAllBytes(path);
      return new String(encoded, "UTF-8");
    } catch (IOException e) {
      throw new EmailException("Email template path not found");
    }
  }
}
