package org.gfa.avustribesbackend.models;

import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BuildingTypeTest {

  private BuildingType buildingType;
  private ResourceType resourceType;

  @BeforeEach
  public void setup() {

    resourceType = new ResourceType();
    List<Building> buildings = new ArrayList<>();
    resourceType.setId(1L);
    buildingType = new BuildingType(BuildingTypeValue.TOWNHALL, resourceType, buildings);
  }

  @Test
  public void test_name_is_set_correctly() {

    BuildingTypeValue name = buildingType.getName();
    assertEquals(BuildingTypeValue.TOWNHALL, name);
  }

  @Test
  public void test_resource_type_is_set_correctly() {

    ResourceType actualResourceType = buildingType.getResourceType();
    assertEquals(resourceType, actualResourceType);
  }

  @Test
  public void test_resource_type_id_is_set_correctly() {

    Long resourceTypeId = buildingType.getResourceType().getId();
    assertEquals(1L, resourceTypeId);
  }

  @Test
  public void test_building_list_is_not_null() {
    assertNotNull(buildingType.getBuildings());
  }
}
