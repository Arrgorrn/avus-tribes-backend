package org.gfa.avustribesbackend.dtos;

import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.Resource;
import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;

public class UpgradeBuildingDTO {

  private Kingdom kingdom;
  private BuildingTypeValue buildingType;

  public UpgradeBuildingDTO() {}

  public UpgradeBuildingDTO(Kingdom kingdom, BuildingTypeValue buildingType, Resource gold) {
    this.kingdom = kingdom;
    this.buildingType = buildingType;
  }

  public Kingdom getKingdom() {
    return kingdom;
  }

  public void setKingdom(Kingdom kingdom) {
    this.kingdom = kingdom;
  }

  public BuildingTypeValue getBuildingType() {
    return buildingType;
  }

  public void setBuildingType(BuildingTypeValue buildingType) {
    this.buildingType = buildingType;
  }
}
