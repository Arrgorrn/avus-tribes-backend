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
  public void sendVerificationEmail(String email) {

    // the email sending logic with the link - the link is the endpoint GET /email/verify/{token}

  }

  @Override
  public boolean verifyEmail(String token) {

    // check it the token is valid? i think
    // if token valid:
    // playerservice.findplayer --> update Verified at
   
    return false; // return true if the verification is ok, false otherwise
  }
}
