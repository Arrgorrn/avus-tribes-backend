package org.gfa.avustribesbackend.services.Building;

import org.gfa.avustribesbackend.exceptions.BuildingException;
import org.gfa.avustribesbackend.models.Building;
import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.Resource;
import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;
import org.gfa.avustribesbackend.repositories.BuildingRepository;
import org.gfa.avustribesbackend.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.gfa.avustribesbackend.models.enums.BuildingTypeValue.*;

@Service
public class BuildingServiceImpl implements BuildingService {
  private final BuildingRepository buildingRepository;
  private final ResourceRepository resourceRepository;

  @Autowired
  public BuildingServiceImpl(
      BuildingRepository buildingRepository, ResourceRepository resourceRepository) {
    this.buildingRepository = buildingRepository;
    this.resourceRepository = resourceRepository;
  }

  @Override
  public void upgradeBuilding(Kingdom kingdom, BuildingTypeValue buildingType, Resource gold) {
    int currentLevel = buildingRepository.getBuildingLevel(kingdom, buildingType);
    int upgradeCost = calculateUpgradeCost(currentLevel, buildingType);
    int maxAllowedLevel = buildingRepository.getBuildingLevel(kingdom, BuildingTypeValue.TOWNHALL);
    Building building = buildingRepository.findByKingdomAndType(kingdom, buildingType);

    if (currentLevel >= 10) {
      throw new BuildingException("Building is already at max level.");
    }

    if (gold.getAmount() < upgradeCost) {
      throw new BuildingException("Not enough gold to upgrade the building.");
    }

    if (buildingType != BuildingTypeValue.TOWNHALL && currentLevel >= maxAllowedLevel) {
      throw new BuildingException("Cannot upgrade the building beyond the Townhall level.");
    }

    gold.setAmount(gold.getAmount() - upgradeCost);
    building.incrementLevel();

    buildingRepository.save(building);
    resourceRepository.save(gold);
  }

  private int calculateUpgradeCost(int currentLevel, BuildingTypeValue buildingType) {
    int baseCost;
    if (buildingType.equals(TOWNHALL)) {
      baseCost = 200;
    } else {
      baseCost = 100;
    }
    return baseCost * currentLevel;
  }
}
