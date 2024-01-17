package org.gfa.avustribesbackend.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class BuildingType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;
  @OneToMany(mappedBy = "buildingType")
  private List<Building> buildings;

  public BuildingType(Long id, List<Building> buildings) {
    this.id = id;
    this.buildings = buildings;
  }

  public BuildingType() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<Building> getBuildings() {
    return buildings;
  }

  public void setBuildings(List<Building> buildings) {
    this.buildings = buildings;
  }
}
