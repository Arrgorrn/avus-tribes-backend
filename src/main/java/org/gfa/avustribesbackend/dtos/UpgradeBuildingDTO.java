package org.gfa.avustribesbackend.dtos;

import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.Resource;
import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;

public class UpgradeBuildingDTO {

  private final Long kingdomId;
  private final BuildingTypeValue buildingType;
  private final Long buildingId;

  public UpgradeBuildingDTO(Long kingdomId, BuildingTypeValue buildingType, Long buildingId) {
    this.kingdomId = kingdomId;
    this.buildingType = buildingType;
    this.buildingId = buildingId;
  }

  public Long getKingdomId() {
    return kingdomId;
  }

  public BuildingTypeValue getBuildingType() {
    return buildingType;
  }

  public Long getBuildingId() {
    return buildingId;
  }
}
