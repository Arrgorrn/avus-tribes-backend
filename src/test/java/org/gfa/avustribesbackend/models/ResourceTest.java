package org.gfa.avustribesbackend.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {

  @Test
  public void test_default_constructor() {

    Resource resource = new Resource();

    Long manuallySetId = 123L;
    resource.setId(manuallySetId);

    assertNotNull(resource);
    assertEquals(manuallySetId, resource.getId());
  }

  @Test
  public void test_constructor_with_parameters() {

    Kingdom kingdom = new Kingdom();
    Production production = new Production();

    int amount = 0;
    Long manuallySetId = 666L;

    Resource resource = new Resource(production, kingdom);
    resource.setId(manuallySetId);

    assertNotNull(resource);
    assertEquals(manuallySetId, resource.getId());
    assertEquals(kingdom, resource.getKingdom());
    assertEquals(production, resource.getProduction());
    assertEquals(amount, resource.getAmount());
  }
}
