package org.gfa.avustribesbackend.services.Kingdom;

import jakarta.servlet.http.HttpServletRequest;
import org.gfa.avustribesbackend.dtos.KingdomResponseDTO;
import org.gfa.avustribesbackend.exceptions.CredentialException;
import org.gfa.avustribesbackend.exceptions.NotFoundException;
import org.gfa.avustribesbackend.exceptions.VerificationException;
import org.gfa.avustribesbackend.models.Building;
import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.Player;
import org.gfa.avustribesbackend.models.Resource;
import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;
import org.gfa.avustribesbackend.models.enums.ResourceTypeValue;
import org.gfa.avustribesbackend.repositories.BuildingRepository;
import org.gfa.avustribesbackend.repositories.KingdomRepository;
import org.gfa.avustribesbackend.repositories.PlayerRepository;
import org.gfa.avustribesbackend.repositories.ResourceRepository;
import org.gfa.avustribesbackend.services.JWT.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KingdomServiceImpl implements KingdomService {
  private final KingdomRepository kingdomRepository;
  private final BuildingRepository buildingRepository;
  private final ResourceRepository resourceRepository;
  private final JwtService jwtService;
  private final PlayerRepository playerRepository;

  @Autowired
  public KingdomServiceImpl(
      KingdomRepository kingdomRepository,
      BuildingRepository buildingRepository,
      ResourceRepository resourceRepository, JwtService jwtService, PlayerRepository playerRepository) {
    this.kingdomRepository = kingdomRepository;
    this.buildingRepository = buildingRepository;
    this.resourceRepository = resourceRepository;
    this.jwtService = jwtService;
    this.playerRepository = playerRepository;
  }

  @Override
  public KingdomResponseDTO createKingdomDTO(Kingdom kingdom) {
    return new KingdomResponseDTO(
        kingdom.getId(),
        kingdom.getWorld().getId(),
        kingdom.getPlayer().getId(),
        kingdom.getName(),
        kingdom.getCoordinateX(),
        kingdom.getCoordinateY(),
        kingdom.getResources(),
        kingdom.getBuildings());
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
  public ResponseEntity<Object> listPlayerKingdoms(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer")) {
      String token = authHeader.substring(7);
      String email = jwtService.extractUsername(token);
      Player player = playerRepository.findByEmailIgnoreCase(email);
      if (player == null) {
        throw new NotFoundException("Player not found.");
      }
      List<KingdomResponseDTO> dtoList = new ArrayList<>();
      List<Kingdom> kingdoms = kingdomRepository.findKingdomsByPlayer(player);
      for (Kingdom kingdom : kingdoms) {
        KingdomResponseDTO dto = createKingdomDTO(kingdom);
        dtoList.add(dto);
      }
      return ResponseEntity.ok(dtoList);
    }
    throw new VerificationException("Incorrect authorization header");
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
    Kingdom kingdom = new Kingdom(player); // TODO: set up a world here !
    kingdomRepository.save(kingdom);
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
