package org.gfa.avustribesbackend.dtos;

import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;

public class BuildNewBuildingDTO {
  private final Kingdom kingdom;
  private final BuildingTypeValue type;

  public BuildNewBuildingDTO(Kingdom kingdom, BuildingTypeValue type) {
    this.kingdom = kingdom;
    this.type = type;
  }

  public Kingdom getKingdom() {
    return kingdom;
  }

  public BuildingTypeValue getType() {
    return type;
  }
}
