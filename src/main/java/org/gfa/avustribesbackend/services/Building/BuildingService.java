package org.gfa.avustribesbackend.services.Building;

import org.gfa.avustribesbackend.dtos.BuildNewBuildingDTO;
import org.gfa.avustribesbackend.dtos.UpgradeBuildingDTO;
import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.Resource;
import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;

public interface BuildingService {

  void upgradeBuilding(UpgradeBuildingDTO dto);

  int getBuildingLevel(UpgradeBuildingDTO dto);

  boolean buildNewBuilding(BuildNewBuildingDTO dto);
}
