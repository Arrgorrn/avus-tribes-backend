package org.gfa.avustribesbackend.models;

import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildingTypeTest {

  private BuildingType buildingType;
  private ResourceType resourceType;

  @BeforeEach
  public void setup() {

    resourceType = new ResourceType();
    resourceType.setId(1L);
    buildingType = new BuildingType(BuildingTypeValue.TOWNHALL, resourceType);
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
}
