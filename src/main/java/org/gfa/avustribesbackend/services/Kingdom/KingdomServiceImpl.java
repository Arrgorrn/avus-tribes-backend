package org.gfa.avustribesbackend.services.Kingdom;

import org.gfa.avustribesbackend.dtos.KingdomResponseDTO;
import org.gfa.avustribesbackend.exceptions.CredentialException;
import org.gfa.avustribesbackend.models.*;
import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;
import org.gfa.avustribesbackend.models.enums.ResourceTypeValue;
import org.gfa.avustribesbackend.repositories.BuildingRepository;
import org.gfa.avustribesbackend.repositories.KingdomRepository;
import org.gfa.avustribesbackend.repositories.ResourceRepository;
import org.gfa.avustribesbackend.repositories.WorldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KingdomServiceImpl implements KingdomService {
  private final KingdomRepository kingdomRepository;
  private final BuildingRepository buildingRepository;
  private final ResourceRepository resourceRepository;
  private final WorldRepository worldRepository;

  @Autowired
  public KingdomServiceImpl(
      KingdomRepository kingdomRepository,
      BuildingRepository buildingRepository,
      ResourceRepository resourceRepository,
      WorldRepository worldRepository) {
    this.kingdomRepository = kingdomRepository;
    this.buildingRepository = buildingRepository;
    this.resourceRepository = resourceRepository;
    this.worldRepository = worldRepository;
  }

  @Override
  public KingdomResponseDTO createKingdomDTO(Kingdom kingdom) {
    return new KingdomResponseDTO(
        kingdom.getId(),
        kingdom.getWorld().getId(),
        kingdom.getPlayer().getId(),
        kingdom.getName(),
        kingdom.getCoordinateX(),
        kingdom.getCoordinateY());
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
  public KingdomResponseDTO returnKingdomDTOById(Long id) {
    Optional<Kingdom> kingdomOptional = kingdomRepository.findById(id);
    if (kingdomOptional.isPresent()) {
      return createKingdomDTO(kingdomOptional.get());
    } else {
      throw new CredentialException("Kingdom not found");
    }
  }

  @Override
  public boolean checkId(Long id) {
    return kingdomRepository.existsById(id);
  }

  @Override
  public void createStartingKingdom(Player player) {
    World databeseWorld = worldRepository.findWorldWithLessThan5Kingdoms();
    Kingdom kingdom;
    if (databeseWorld == null) {
      World world = new World();
      worldRepository.save(world);

      kingdom = new Kingdom(player, world);
      List<Kingdom> kingdoms = new ArrayList<>();
      kingdoms.add(kingdom);

      world.setKingdoms(kingdoms);

      worldRepository.save(world);
      kingdomRepository.save(kingdom);
    } else {
      kingdom = new Kingdom(player, databeseWorld);

      List<Kingdom> kingdoms = databeseWorld.getKingdoms();
      kingdoms.add(kingdom);
      databeseWorld.setKingdoms(kingdoms);

      worldRepository.save(databeseWorld);
      kingdomRepository.save(kingdom);
    }
    for (BuildingTypeValue buildingType : BuildingTypeValue.values()) {
      Building building = new Building(kingdom, buildingType);
      buildingRepository.save(building);
    }
    for (ResourceTypeValue resourceType : ResourceTypeValue.values()) {
      Resource resource = new Resource(kingdom, resourceType, 100);
      resourceRepository.save(resource);
    }
  }
}
