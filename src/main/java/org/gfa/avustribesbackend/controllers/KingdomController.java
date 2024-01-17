package org.gfa.avustribesbackend.controllers;

import org.gfa.avustribesbackend.exceptions.CredentialException;
import org.gfa.avustribesbackend.services.Kingdom.KingdomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(kingdomService.listKingdoms(), HttpStatus.OK);
    }

    @GetMapping("/kingdoms/{id}")
    public ResponseEntity<Object> index(@PathVariable Long id) {
        if (kingdomService.checkId(id)){
            return new ResponseEntity<>(kingdomService.findKingdomDTOById(id),HttpStatus.OK);
        } else {
            throw new CredentialException("Kingdom not found");
        }
    }
}
