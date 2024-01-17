package org.gfa.avustribesbackend.services.Kingdom;

import org.gfa.avustribesbackend.dtos.KingdomResponseDTO;
import org.gfa.avustribesbackend.dtos.PlayerInfoDTO;
import org.gfa.avustribesbackend.exceptions.CredentialException;
import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.Player;
import org.gfa.avustribesbackend.repositories.KingdomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KingdomServiceImpl implements KingdomService {
  private final KingdomRepository kingdomRepository;

  @Autowired
  public KingdomServiceImpl(KingdomRepository kingdomRepository) {
    this.kingdomRepository = kingdomRepository;
  }

  @Override
  public KingdomResponseDTO createKingdomDTO(Kingdom kingdom) {
    return new KingdomResponseDTO(
        kingdom.getId(),
        kingdom.getWorld().getId(),
        kingdom.getPlayer().getId(),
        kingdom.getName());
  }

  @Override
  public List<KingdomResponseDTO> listKingdoms() {
    List<Kingdom> allPlayers = kingdomRepository.findAll();
    List<KingdomResponseDTO> dtoList = new ArrayList<>();
    for (Kingdom kingdom : allPlayers) {
      KingdomResponseDTO dto = createKingdomDTO(kingdom);
      dtoList.add(dto);
    }
    return dtoList;
  }

  @Override
  public KingdomResponseDTO findKingdomDTOById(Long id) {
    Optional<Kingdom> kingdomOptional = kingdomRepository.findById(id);
    if (kingdomOptional.isPresent()){
      KingdomResponseDTO dto = createKingdomDTO(kingdomOptional.get());
      return dto;
    } else {
      throw new CredentialException("Kingdom not found");
    }
  }

  @Override
  public boolean checkId(Long id) {
    return kingdomRepository.existsById(id) ;
  }
}
