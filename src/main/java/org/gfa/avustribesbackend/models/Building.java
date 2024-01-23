package org.gfa.avustribesbackend.models;

import jakarta.persistence.*;

@Entity
public class Building {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "type_id", nullable = false)
  private BuildingType buildingType;

  @ManyToOne
  @JoinColumn(name = "kingdom_id", nullable = false)
  private Kingdom kingdom;

  @ManyToOne
  @JoinColumn(name = "production_id")
  private Production production;

  private Integer level;

  public Building(
      Long id, BuildingType buildingType, Kingdom kingdom, Production productions, Integer level) {
    this.id = id;
    this.buildingType = buildingType;
    this.kingdom = kingdom;
    this.production = productions;
    this.level = level;
  }

  public Building() {
    this.level = 1;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BuildingType getBuildingType() {
    return buildingType;
  }

  public void setBuildingType(BuildingType buildingType) {
    this.buildingType = buildingType;
  }

  public Kingdom getKingdom() {
    return kingdom;
  }

  public void setKingdom(Kingdom kingdom) {
    this.kingdom = kingdom;
  }

  public Production getProductions() {
    return production;
  }

  public void setProductions(Production productions) {
    this.production = productions;
  }

  public Integer getLevel() {
    return level;
  }

  public void setLevel(Integer level) {
    if (level > 0) {
      this.level = level;
    } else {
      this.level = 1;
    }
  }
}
