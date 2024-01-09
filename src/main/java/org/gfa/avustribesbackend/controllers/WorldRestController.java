package org.gfa.avustribesbackend.controllers;

import org.gfa.avustribesbackend.services.World.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorldRestController {
  private final WorldService worldService;
  @Autowired
  public WorldRestController(WorldService worldService) {
    this.worldService = worldService;
  }

  @GetMapping("/worlds")
  public ResponseEntity<Object> index() {
    return worldService.index();
  }

  @GetMapping("/worlds/create")
  public ResponseEntity<Object> create() {
    return null;
  }

  @GetMapping("/worlds/{id}")
  public ResponseEntity<Object> show() {
    return null;
  }

  @GetMapping("/worlds/{id}/edit")
  public ResponseEntity<Object> edit() {
    return null;
  }

  @PutMapping("/worlds/{id}")
  public ResponseEntity<Object> update() {
    return null;
  }

  @DeleteMapping("/worlds/{id}")
  public ResponseEntity<Object> destroy() {
    return null;
  }
}
