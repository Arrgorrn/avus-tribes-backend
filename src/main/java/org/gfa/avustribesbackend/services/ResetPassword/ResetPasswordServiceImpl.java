package org.gfa.avustribesbackend.services.ResetPassword;

import org.gfa.avustribesbackend.dtos.EmailDTO;
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

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final JavaMailSender javaMailSender;
    private final PlayerRepository playerRepository;
    private final PlayerService playerService;

    @Autowired
    public ResetPasswordServiceImpl(JavaMailSender javaMailSender, PlayerRepository playerRepository, PlayerService playerService) {
        this.javaMailSender = javaMailSender;
        this.playerRepository = playerRepository;
        this.playerService = playerService;
    }

    @Override
    public ResponseEntity<Object> sendResetPasswordEmail(EmailDTO email) {
        if (email.getEmail().isEmpty() || !playerRepository.existsByEmailIgnoreCase(email.getEmail())) {
            throw new CredentialException("Invalid email!");
        }
        Player player = playerRepository.findByEmailIgnoreCase(email.getEmail());
//        if (player.getVerifiedAt() == null) {
//            throw new VerificationException("Unverified email!");
//        }

        player.setForgottenPasswordToken(playerService.verificationToken());

        long currentTimeMillis = System.currentTimeMillis() + 1000 * 60 * 60;
        player.setForgottenPasswordTokenExpiresAt(new Date(currentTimeMillis));

        playerRepository.save(player);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(player.getEmail());
        message.setSubject("Password reset");
        message.setText("Hello " + player.getUserName() + ". If you want to reset your password please click on this link: http://localhost:8080/reset-password/" + player.getForgottenPasswordToken());

        javaMailSender.send(message);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Object> resetPassword(String token) {
        Player player = playerRepository.findByForgottenPasswordToken(token);

        if (player == null) {
            throw new VerificationException("Invalid token!");
        }

        String newPassword = generatePassword();
        player.setPassword(newPassword);

        playerRepository.save(player);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(player.getEmail());
        message.setSubject("Password reset");
        message.setText("Your new password is: " + newPassword);

        javaMailSender.send(message);

        return ResponseEntity.ok().build();
    }

    @Override
    public String generatePassword() {

        final char[] lowercase = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        final char[] uppercase = "ABCDEFGJKLMNPRSTUVWXYZ".toCharArray();
        final char[] numbers = "0123456789".toCharArray();
        final char[] symbols = "^$?!@#%&".toCharArray();
        final char[] allAllowed = "abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ0123456789^$?!@#%&".toCharArray();

        Random random = new SecureRandom();

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            password.append(allAllowed[random.nextInt(allAllowed.length)]);
        }

        password.insert(random.nextInt(password.length()), lowercase[random.nextInt(lowercase.length)]);
        password.insert(random.nextInt(password.length()), uppercase[random.nextInt(uppercase.length)]);
        password.insert(random.nextInt(password.length()), numbers[random.nextInt(numbers.length)]);
        password.insert(random.nextInt(password.length()), symbols[random.nextInt(symbols.length)]);

        return password.toString();
    }
}

