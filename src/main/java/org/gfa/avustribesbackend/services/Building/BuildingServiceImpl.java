package org.gfa.avustribesbackend.services.Building;

import org.gfa.avustribesbackend.dtos.BuildNewBuildingDTO;
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
  private static final int townhallCost = 200;
  private static final int farmCost = 100;
  private static final int mineCost = 100;
  private static final int academyCost = 150;

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

  @Override
  public boolean buildNewBuilding(BuildNewBuildingDTO dto) {
    if (isBuildingPossible(dto)) {
      Building building = new Building(dto.getKingdom(), dto.getType());
      buildingRepository.save(building);
      return true;
    } else {
      throw new BuildingException("Building not built");
    }
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

  private boolean isBuildingPossible(BuildNewBuildingDTO dto) {
    Kingdom kingdom = dto.getKingdom();
    int gold = getGoldAmount(kingdom);
    BuildingTypeValue type = dto.getType();
    int buildingCost = getBuildingCost(type);
    Building townhall = buildingRepository.findByKingdomAndType(kingdom, TOWNHALL);
    boolean hasTownhall = townhall != null;
    if (hasTownhall && type.equals(TOWNHALL)) {
      throw new BuildingException("You already have a townhall!");
    }
    if (!hasTownhall && !type.equals(TOWNHALL)) {
      throw new BuildingException("You need to build a townhall first!");
    }
    if (gold >= buildingCost) {
      return true;
    } else {
      throw new BuildingException("You don't have enough gold!");
    }
  }

  private int getBuildingCost(BuildingTypeValue type) {
    if (type.equals(TOWNHALL)) {
      return townhallCost;
    }
    if (type.equals(FARM)) {
      return farmCost;
    }
    if (type.equals(MINE)) {
      return mineCost;
    }
    if (type.equals(ACADEMY)) {
      return academyCost;
    } else {
      throw new BuildingException("Building not found");
    }
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
}
