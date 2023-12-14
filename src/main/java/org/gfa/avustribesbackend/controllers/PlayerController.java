package org.gfa.avustribesbackend.controllers;

import org.gfa.avustribesbackend.dtos.PlayerRegistrationBody;
import org.gfa.avustribesbackend.services.Player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
