package org.gfa.avustribesbackend.services;

import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

  private final PlayerRepository playerRepository;

  @Autowired
  public EmailVerificationServiceImpl(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }


    @Override
    public boolean verifyEmail(String token) {
      //find the player

      //if player not null, update verifiedAt, send email, return true


      sendVerificationEmail(playerRepository.findByVerificationToken(token).getEmail());

      //if player not found, return false
      return false;
    }

    @Override
    public void sendVerificationEmail(String email) {

    }


}
