package org.gfa.avustribesbackend.services.Building;

import org.gfa.avustribesbackend.exceptions.BuildingException;
import org.gfa.avustribesbackend.models.Building;
import org.gfa.avustribesbackend.models.Kingdom;
import org.gfa.avustribesbackend.models.Resource;
import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;
import org.gfa.avustribesbackend.models.enums.ResourceTypeValue;
import org.gfa.avustribesbackend.repositories.BuildingRepository;
import org.gfa.avustribesbackend.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
  public void upgradeBuilding(Kingdom kingdom, BuildingTypeValue buildingType) {

    int currentLevel = buildingRepository.getBuildingLevel(kingdom, buildingType);
    int upgradeCost = calculateUpgradeCost(currentLevel, buildingType);
    int maxAllowedLevel = buildingRepository.getBuildingLevel(kingdom, BuildingTypeValue.TOWNHALL);
    Building building = buildingRepository.findByKingdomAndType(kingdom, buildingType);
    int gold = getGoldAmount(kingdom);

    if (currentLevel >= 10) {
      throw new BuildingException("Building is already at max level.");
    }

    if (gold < upgradeCost) {
      throw new BuildingException("Not enough gold to upgrade the building.");
    }

    if (buildingType != BuildingTypeValue.TOWNHALL && currentLevel >= maxAllowedLevel) {
      throw new BuildingException("Cannot upgrade the building beyond the Townhall level.");
    }

    gold -= upgradeCost;

    building.incrementLevel();

    buildingRepository.save(building);

    updateGoldAmountInRepository(kingdom, gold);
  }

  @Override
  public int getBuildingLevel(Kingdom kingdom, BuildingTypeValue buildingType) {
    return buildingRepository.getBuildingLevel(kingdom, buildingType);
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

  private int getGoldAmount(Kingdom kingdom) {
    List<Resource> resources = kingdom.getResources();
    for (Resource resource : resources) {
      if (ResourceTypeValue.GOLD.equals(resource.getType())) {
        return resource.getAmount();
      }
    }
    return 0;
  }

  private void updateGoldAmountInRepository(Kingdom kingdom, int goldAmount) {
    List<Resource> resources = kingdom.getResources();
    for (Resource resource : resources) {
      if (ResourceTypeValue.GOLD.equals(resource.getType())) {
        resource.setAmount(goldAmount);
        resourceRepository.save(resource);
        return;
      }
    }
  }
}
