package org.gfa.avustribesbackend.services.Kingdom;

import jakarta.servlet.http.HttpServletRequest;
import org.gfa.avustribesbackend.dtos.KingdomResponseDTO;
import org.gfa.avustribesbackend.dtos.TokenDTO;
import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.Player;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KingdomService {
  KingdomResponseDTO createKingdomDTO(Kingdom kingdom);

  List<KingdomResponseDTO> listKingdoms();
  ResponseEntity<Object> listPlayerKingdoms(HttpServletRequest request);

  KingdomResponseDTO returnKingdomDTOById(Long id);

  boolean checkId(Long id);

  void createStartingKingdom(Player player);
}
