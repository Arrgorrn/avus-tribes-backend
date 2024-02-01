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

import java.util.concurrent.CompletableFuture;

@RestController
public class BuildingController {

  private final BuildingService buildingService;

  @Autowired
  public BuildingController(BuildingService buildingService) {
    this.buildingService = buildingService;
  }

  @PatchMapping("/kingdoms/upgrade")
  public CompletableFuture<ResponseEntity<Object>> upgradeBuildingAsync(
      @RequestBody UpgradeBuildingDTO dto) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            Kingdom kingdom = dto.getKingdom();
            BuildingTypeValue buildingType = dto.getBuildingType();
            int upgradeTime = buildingService.getBuildingLevel(kingdom, buildingType);

            buildingService.upgradeBuilding(kingdom, buildingType);

            Thread.sleep(upgradeTime * 60 * 1000);

            return ResponseEntity.ok(
                "Building upgrade started for "
                    + dto.getBuildingType()
                    + ". Time required: "
                    + upgradeTime
                    + " minutes.");
          } catch (BuildingException be) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(be.getMessage());
          } catch (InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error during building upgrade");
          }
        });
  }
}
