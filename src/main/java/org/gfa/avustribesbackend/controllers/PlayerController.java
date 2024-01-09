package org.gfa.avustribesbackend.controllers;

import org.gfa.avustribesbackend.dtos.EmailDTO;
import org.gfa.avustribesbackend.dtos.PlayerRegistrationBody;
import org.gfa.avustribesbackend.dtos.StatusDTO;
import org.gfa.avustribesbackend.dtos.TokenDTO;
import org.gfa.avustribesbackend.services.Player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayerController {
  private final PlayerService playerService;

  @Autowired
  public PlayerController(PlayerService playerService) {
    this.playerService = playerService;
  }

  @PostMapping("/register")
  public ResponseEntity<Object> register(@RequestBody PlayerRegistrationBody request) {
    return playerService.registerPlayer(request);
  }

  @PostMapping("/reset-password")
  public ResponseEntity<Object> passwordReset(@RequestBody EmailDTO email) {
    return ResponseEntity.ok().body(new StatusDTO("ok"));
  }

  @GetMapping("/reset-password/{token}")
  public ResponseEntity<Object> passwordReset(@PathVariable TokenDTO token) {
    return ResponseEntity.ok().body(token);
  }
}
