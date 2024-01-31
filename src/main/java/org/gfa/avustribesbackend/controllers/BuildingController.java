package org.gfa.avustribesbackend.controllers;

import org.gfa.avustribesbackend.services.Building.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuildingController {

  private final BuildingService buildingService;

  @Autowired
  public BuildingController(BuildingService buildingService) {
    this.buildingService = buildingService;
  }

  @PatchMapping("/kingdoms/upgrade")
  public ResponseEntity<Object> upgradeBuilding() {

  }
}
