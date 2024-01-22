package org.gfa.avustribesbackend.services.Kingdom;

import org.gfa.avustribesbackend.dtos.KingdomResponseDTO;
import org.gfa.avustribesbackend.models.Kingdom;
import java.util.List;

public interface KingdomService {
  KingdomResponseDTO createKingdomDTO(Kingdom kingdom);

  List<KingdomResponseDTO> listKingdoms();

  KingdomResponseDTO returnKingdomDTOById(Long id);

  boolean checkId(Long id);
}
