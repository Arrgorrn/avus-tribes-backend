package org.gfa.avustribesbackend.dtos;

import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.Resource;
import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;

public class UpgradeBuildingDTO {

  private final Long kingdomId;
  private final BuildingTypeValue buildingType;

  public UpgradeBuildingDTO(Long kingdomId, BuildingTypeValue buildingType) {
    this.kingdomId = kingdomId;
    this.buildingType = buildingType;
  }

  public Long getKingdomId() {
    return kingdomId;
  }

  public BuildingTypeValue getBuildingType() {
    return buildingType;
  }
}
