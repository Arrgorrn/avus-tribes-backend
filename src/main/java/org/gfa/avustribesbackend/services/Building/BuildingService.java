package org.gfa.avustribesbackend.services.Building;

import org.gfa.avustribesbackend.dtos.BuildNewBuildingDTO;
import org.gfa.avustribesbackend.dtos.UpgradeBuildingDTO;

public interface BuildingService {

  void upgradeBuilding(UpgradeBuildingDTO dto);

  int getBuildingLevel(UpgradeBuildingDTO dto);

  boolean buildNewBuilding(BuildNewBuildingDTO dto);
}
