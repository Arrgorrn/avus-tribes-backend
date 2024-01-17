package org.gfa.avustribesbackend.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {

  Building building;

  @BeforeEach
  void setup() {
    building = new Building(
        1L,
        new BuildingType(),
        new Kingdom(),
        new Production(),
        1);
  }

  @Test
  void all_values_are_set_correctly() {
    Long expectedId = 1L;
    int expectedLevel = 1;

    assertEquals(building.getId(), expectedId);
    assertNotNull(building.getBuildingType());
    assertNotNull(building.getKingdom());
    assertNotNull(building.getProductions());
    assertEquals(building.getLevel(), expectedLevel);
  }

  @Test
  void level_is_set_to_1_by_default() {
    building = new Building();

    int expectedLevel = 1;

    assertEquals(building.getLevel(), expectedLevel);
  }

  @Test
  void level_is_set_to_1_when_passed_value_is_less_than_1() {
    building.setLevel(-5);
    int expectedLevel = 1;

    assertEquals(building.getLevel(), expectedLevel);
  }
}