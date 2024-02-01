package org.gfa.avustribesbackend.controllers;

import org.gfa.avustribesbackend.dtos.UpgradeBuildingDTO;
import org.gfa.avustribesbackend.exceptions.BuildingException;
import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.Resource;
import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;
import org.gfa.avustribesbackend.services.Building.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildingController {

  private final BuildingService buildingService;

  @Autowired
  public BuildingController(BuildingService buildingService) {
    this.buildingService = buildingService;
  }

  @PatchMapping("/kingdoms/upgrade")
  public ResponseEntity<Object> upgradeBuilding(
      @RequestBody UpgradeBuildingDTO upgradeBuildingDTO) {
    try {
      Kingdom kingdom = upgradeBuildingDTO.getKingdom();
      BuildingTypeValue buildingType = upgradeBuildingDTO.getBuildingType();

      buildingService.upgradeBuilding(kingdom, buildingType);

      int upgradeTime = buildingService.getBuildingLevel(kingdom, buildingType);
      //add logic here to delay with upgradeTime minutes

      return ResponseEntity.ok(
          "Building upgrade started. Time required: " + upgradeTime + " minutes.");
    } catch (BuildingException be) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(be.getMessage());
    }
  }
}
