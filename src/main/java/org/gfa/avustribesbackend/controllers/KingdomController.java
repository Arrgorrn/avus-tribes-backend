package org.gfa.avustribesbackend.controllers;

import org.gfa.avustribesbackend.dtos.BuildNewBuildingDTO;
import org.gfa.avustribesbackend.exceptions.BuildingException;
import org.gfa.avustribesbackend.exceptions.CredentialException;
import org.gfa.avustribesbackend.services.Building.BuildingService;
import org.gfa.avustribesbackend.services.Kingdom.KingdomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class KingdomController {
  private final KingdomService kingdomService;
  private final BuildingService buildingService;

  @Autowired
  public KingdomController(KingdomService kingdomService, BuildingService buildingService) {
    this.kingdomService = kingdomService;
    this.buildingService = buildingService;
  }

  @GetMapping("/kingdoms")
  public ResponseEntity<Object> index() {
    return new ResponseEntity<>(kingdomService.listKingdoms(), HttpStatusCode.valueOf(200));
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

  @PostMapping("/kingdoms/build")
  public ResponseEntity<Object> buildNewBuilding(@RequestBody BuildNewBuildingDTO dto) {
    if (buildingService.buildNewBuilding(dto)) {
      return new ResponseEntity<>(
          "New " + dto.getType() + " successfully built!", HttpStatusCode.valueOf(200));
    } else {
      throw new BuildingException("Building not built");
    }
  }
}
