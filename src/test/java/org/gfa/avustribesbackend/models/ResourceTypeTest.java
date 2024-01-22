package org.gfa.avustribesbackend.models;

import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;
import org.gfa.avustribesbackend.models.enums.ResourceTypeValue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTypeTest {
  static BuildingType buildingType;
  static ResourceType resourceType;

  @BeforeAll
  public static void start() {
    List<Building> buildings = new ArrayList<>();
    buildingType = new BuildingType(BuildingTypeValue.MINE, buildings);
    resourceType = new ResourceType(ResourceTypeValue.GOLD, buildingType);
  }

  @Test
  void construction_test() {
    // Arrange
    Long resourceId = 1L;
    String type = ResourceTypeValue.GOLD.name();

    // Act
    resourceType.setId(resourceId);
    // Asserts
    assertNotNull(resourceType);
    assertEquals(resourceId, resourceType.getId());
    assertEquals(type, resourceType.getName().name());
    assertEquals(buildingType, resourceType.getBuildingType());
  }
}
