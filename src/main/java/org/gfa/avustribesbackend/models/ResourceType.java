package org.gfa.avustribesbackend.models;

import jakarta.persistence.*;

@Entity
public class ResourceType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Id", unique = true, nullable = false)
  private Long id;

  @OneToOne(mappedBy = "resourceType", cascade = CascadeType.ALL)
  private BuildingType buildingType;

  public ResourceType() {}

  public ResourceType(BuildingType buildingType) {
    this.buildingType = buildingType;
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
}
