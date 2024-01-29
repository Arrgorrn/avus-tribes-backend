package org.gfa.avustribesbackend.services.Building;

import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.Player;
import org.gfa.avustribesbackend.models.Resource;
import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;

public interface BuildingService {
  void upgradeBuilding(Kingdom kingdom, BuildingTypeValue buildingType, Resource gold);
}
