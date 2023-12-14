package org.gfa.avustribesbackend.controllers;

import org.gfa.avustribesbackend.services.Email.EmailVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

  private final EmailVerificationService emailVerificationService;

  public PlayerController(EmailVerificationService emailVerificationService) {
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
}
