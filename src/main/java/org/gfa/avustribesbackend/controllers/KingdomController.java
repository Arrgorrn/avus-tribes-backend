package org.gfa.avustribesbackend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.gfa.avustribesbackend.exceptions.CredentialException;
import org.gfa.avustribesbackend.services.Kingdom.KingdomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KingdomController {
  private final KingdomService kingdomService;

  @Autowired
  public KingdomController(KingdomService kingdomService) {
    this.kingdomService = kingdomService;
  }

  @GetMapping("/kingdoms")
  public ResponseEntity<Object> index() {
    return new ResponseEntity<>(kingdomService.listKingdoms(), HttpStatusCode.valueOf(200));
  }

  @GetMapping("/kingdoms/player")
  public ResponseEntity<Object> playerIndex(HttpServletRequest request) {
    return kingdomService.listPlayerKingdoms(request);
  }

  @GetMapping("/kingdoms/{id}")
  public ResponseEntity<Object> index(@PathVariable Long id) {
    if (kingdomService.checkId(id)) {
      return new ResponseEntity<>(
          kingdomService.returnKingdomDTOById(id), HttpStatusCode.valueOf(200));
    } else {
      throw new CredentialException("Kingdom not found");
    }
  }
}
