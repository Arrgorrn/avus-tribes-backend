package org.gfa.avustribesbackend.controllers;

import org.gfa.avustribesbackend.services.Email.EmailVerificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.gfa.avustribesbackend.dtos.PlayerRegistrationBody;
import org.gfa.avustribesbackend.services.Player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

  private final EmailVerificationService emailVerificationService;
  private final PlayerService playerService;

  @Autowired
  public PlayerController(
      PlayerService playerService, EmailVerificationService emailVerificationService) {
    this.playerService = playerService;
    this.emailVerificationService = emailVerificationService;
  }

  @GetMapping("/email/verify/{token}")
  public ResponseEntity<Object> verifyEmail(@PathVariable String token) {
    if (emailVerificationService.verifyEmail(token)) {
      return ResponseEntity.ok().body("ok");
    } else {
      // request for a new token?
      return ResponseEntity.badRequest().body("not ok, some error message or exception");
    }
  }

  @PostMapping("/register")
  public ResponseEntity<Object> register(@RequestBody PlayerRegistrationBody request) {
    return playerService.registerPlayer(request);
  }
}
