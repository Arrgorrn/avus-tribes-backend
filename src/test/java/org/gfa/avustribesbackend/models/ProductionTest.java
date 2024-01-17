package org.gfa.avustribesbackend.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductionTest {

  @Test
  public void test_default_constructor() {

    Production production = new Production();

    Long manuallySetId = 123L;
    production.setId(manuallySetId);

    assertNotNull(production);
    assertEquals(manuallySetId, production.getId());
  }

  @Test
  public void test_constructor_with_parameters() {

    Kingdom kingdom = new Kingdom();
    List<Building> buildings = new ArrayList<>();
    Resource resource = new Resource();
    int amount = 10;
    boolean collected = false;
    LocalDateTime startedAt = LocalDateTime.now();
    LocalDateTime completedAt = LocalDateTime.now().plusHours(2);

    Long manuallySetId = 666L;

    Production production =
        new Production(kingdom, buildings, resource, amount, collected, startedAt, completedAt);
    production.setId(manuallySetId);

    assertNotNull(production);
    assertEquals(manuallySetId, production.getId());
    assertEquals(kingdom, production.getKingdom());
    assertEquals(buildings, production.getBuildings());
    assertEquals(resource, production.getResource());
    assertEquals(amount, production.getAmount());
    assertEquals(collected, production.isCollected());
    assertEquals(startedAt, production.getStartedAt());
    assertEquals(completedAt, production.getCompletedAt());
  }
}
