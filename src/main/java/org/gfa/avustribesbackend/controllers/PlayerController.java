package org.gfa.avustribesbackend.controllers;

import org.gfa.avustribesbackend.dtos.EmailDTO;
import org.gfa.avustribesbackend.services.Email.EmailVerificationService;
import org.gfa.avustribesbackend.services.ResetPassword.ResetPasswordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.gfa.avustribesbackend.dtos.PlayerRegistrationBody;
import org.gfa.avustribesbackend.dtos.StatusDTO;
import org.gfa.avustribesbackend.dtos.TokenDTO;
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
          PlayerService playerService, EmailVerificationService emailVerificationService, ResetPasswordService resetPasswordService) {
    this.playerService = playerService;
    this.emailVerificationService = emailVerificationService;
    this.resetPasswordService = resetPasswordService;
  }

  @GetMapping("/email/verify/{token}")
  public ResponseEntity<Object> verifyEmail(@PathVariable String token) {
    if (emailVerificationService.verifyEmail(token)) {
      return ResponseEntity.ok().body("ok");
    } else {
      // request for a new token? Gerzson?
      return ResponseEntity.badRequest().body("not ok, some error message or exception");
    }
  }

  @PostMapping("/register")
  public ResponseEntity<Object> register(@RequestBody PlayerRegistrationBody request) {
    return playerService.registerPlayer(request);
  }

  @PostMapping("/reset-password")
  public ResponseEntity<Object> resetPassword(@RequestBody EmailDTO email) {
    return resetPasswordService.resetPassword(email);
  }

  @GetMapping("/reset-password/{token}")
  public ResponseEntity<Object> resetPassword(@PathVariable TokenDTO token) {
    return ResponseEntity.ok().body(token);
  }
}
