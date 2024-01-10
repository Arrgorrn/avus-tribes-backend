package org.gfa.avustribesbackend.services.ResetPassword;

import org.gfa.avustribesbackend.dtos.EmailDTO;
import org.gfa.avustribesbackend.dtos.StatusDTO;
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
    public ResetPasswordServiceImpl(JavaMailSender javaMailSender, PlayerRepository playerRepository, PlayerService playerService) {
        this.javaMailSender = javaMailSender;
        this.playerRepository = playerRepository;
        this.playerService = playerService;
    }

    @Override
    public ResponseEntity<Object> resetPassword(EmailDTO email) {
        if (email == null || email.getEmail().isEmpty() || !playerRepository.existsByEmailIgnoreCase(email.getEmail())) {
            throw new CredentialException("Invalid email!");
        }
        Player player = playerRepository.findByEmailIgnoreCase(email.getEmail());
        if (player.getVerifiedAt() == null) {
            throw new VerificationException("Unverified email!");
        }

        player.setForgottenPasswordToken(playerService.verificationToken());

        long currentTimeMillis = System.currentTimeMillis() + 1000 * 60 * 60;
        player.setForgottenPasswordTokenExpiresAt(new Date(currentTimeMillis));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(player.getEmail());
        message.setSubject("Password Reset");
        message.setText("Hello " + player.getUserName() + "If you want to reset your password click on this link: http://localhost:8080/reset-password/" + player.getForgottenPasswordToken()); // insert Gerzson's awesome method here

        javaMailSender.send(message);

        return ResponseEntity.ok(new StatusDTO("ok"));
    }
}
