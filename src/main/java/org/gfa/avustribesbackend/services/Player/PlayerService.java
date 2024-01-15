package org.gfa.avustribesbackend.services.Player;

import org.gfa.avustribesbackend.dtos.PlayerInfoDTO;
import org.gfa.avustribesbackend.dtos.PlayerRegistrationBody;
import org.gfa.avustribesbackend.models.Player;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PlayerService {
  ResponseEntity<Object> registerPlayer(PlayerRegistrationBody request);

  boolean validateEmail(String email);

  String verificationToken();
  PlayerInfoDTO createPlayerInfoDTO(Player player);
  List<PlayerInfoDTO> listPlayerInfoDTO();

}
