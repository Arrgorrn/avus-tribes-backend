package org.gfa.avustribesbackend.dtos;

import org.gfa.avustribesbackend.models.Building;
import org.gfa.avustribesbackend.models.Resource;

import java.util.List;

public class KingdomResponseDTO {
  private final Long id;
  private final Long world;
  private final Long owner;
  private final String name;
  private final Double coordinateX;
  private final Double coordinateY;
  private final List<Resource> resources;
  private final List<Building> buildings;

  public KingdomResponseDTO(
      Long id, Long world, Long owner, String name, Double coordinateX, Double coordinateY, List<Resource> resources, List<Building> buildings) {
    this.id = id;
    this.world = world;
    this.owner = owner;
    this.name = name;
    this.coordinateX = coordinateX;
    this.coordinateY = coordinateY;
    this.resources = resources;
    this.buildings = buildings;
  }

  public Long getId() {
    return id;
  }

  public Long getWorld() {
    return world;
  }

  public Long getOwner() {
    return owner;
  }

  public String getName() {
    return name;
  }

  public List<Resource> getResources() {
    return resources;
  }

  public List<Building> getBuildings() {
    return buildings;
  }
}
