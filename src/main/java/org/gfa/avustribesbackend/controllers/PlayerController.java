package org.gfa.avustribesbackend.controllers;

import org.gfa.avustribesbackend.dtos.EmailDTO;
import org.gfa.avustribesbackend.dtos.PasswordRequestDTO;
import org.gfa.avustribesbackend.dtos.TokenRequestDTO;
import org.gfa.avustribesbackend.exceptions.EmailException;
import org.gfa.avustribesbackend.exceptions.VerificationException;
import org.gfa.avustribesbackend.services.Email.EmailVerificationService;
import org.gfa.avustribesbackend.services.ResetPassword.ResetPasswordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.gfa.avustribesbackend.dtos.PlayerRegistrationBody;
import org.gfa.avustribesbackend.services.Player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayerController {

  private final EmailVerificationService emailVerificationService;
  private final PlayerService playerService;
  private final ResetPasswordService resetPasswordService;

  @Autowired
  public PlayerController(
      PlayerService playerService,
      EmailVerificationService emailVerificationService,
      ResetPasswordService resetPasswordService) {
    this.playerService = playerService;
    this.emailVerificationService = emailVerificationService;
    this.resetPasswordService = resetPasswordService;
  }

  @GetMapping("/email/verify/{token}")
  public ResponseEntity<Object> verifyEmail(@PathVariable String token) {
    if (!emailVerificationService.isVerified(token)) {
      if (emailVerificationService.verifyEmail(token)) {
        return ResponseEntity.ok().body("User successfully verified");
      } else {
        throw new VerificationException("Verification failed, expired token");
        // redirect to Gerzson's method?
      }
    } else {
      throw new EmailException("User already verified");
    }
  }

  @PostMapping("/register")
  public ResponseEntity<Object> register(@RequestBody PlayerRegistrationBody request) {
    return playerService.registerPlayer(request);
  }

  @PostMapping("/reset-password")
  public ResponseEntity<Object> sendResetPasswordEmail(@RequestBody EmailDTO email) {
    return resetPasswordService.sendResetPasswordEmail(email);
  }

  @PostMapping("/reset-password/{token}")
  public ResponseEntity<Object> resetPassword(@PathVariable TokenRequestDTO token,
                                              @RequestBody PasswordRequestDTO password) {
    return resetPasswordService.resetPassword(token, password);
  }
}
