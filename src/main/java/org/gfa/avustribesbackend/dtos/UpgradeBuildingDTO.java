package org.gfa.avustribesbackend.dtos;

import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.Resource;
import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;

public class UpgradeBuildingDTO {

  private Kingdom kingdom;
  private BuildingTypeValue buildingType;
  private Resource gold;

  public UpgradeBuildingDTO() {}

  public UpgradeBuildingDTO(Kingdom kingdom, BuildingTypeValue buildingType, Resource gold) {
    this.kingdom = kingdom;
    this.buildingType = buildingType;
    this.gold = gold;
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

  public Resource getGold() {
    return gold;
  }

  public void setGold(Resource gold) {
    this.gold = gold;
  }
}
