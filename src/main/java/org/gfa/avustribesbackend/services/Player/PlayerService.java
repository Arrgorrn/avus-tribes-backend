package org.gfa.avustribesbackend.services.Player;

import org.gfa.avustribesbackend.dtos.PlayerRegistrationBody;
import org.springframework.http.ResponseEntity;

public interface PlayerService {
  ResponseEntity<Object> registerPlayer(PlayerRegistrationBody request);

  boolean validateEmail(String email);

  String verificationToken();
}
