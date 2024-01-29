package org.gfa.avustribesbackend.repositories;

import org.gfa.avustribesbackend.models.Building;
import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {
  List<Building> findAllByKingdomAndType(Kingdom kingdom, BuildingTypeValue buildingTypeValue);

  int getBuildingLevel(Kingdom kingdom, BuildingTypeValue buildingType);

  void updateBuildingLevel(Kingdom kingdom, BuildingTypeValue buildingType, int i);
}
