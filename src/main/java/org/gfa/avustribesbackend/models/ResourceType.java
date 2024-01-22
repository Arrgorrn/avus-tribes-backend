package org.gfa.avustribesbackend.models;

import jakarta.persistence.*;
import org.gfa.avustribesbackend.models.enums.ResourceTypeValue;

@Entity
public class ResourceType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Id", unique = true, nullable = false)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(unique = true, nullable = false)
  private ResourceTypeValue name;

  @OneToOne(mappedBy = "resourceType", cascade = CascadeType.ALL)
  private BuildingType buildingType;

  public ResourceType() {}

  public ResourceType(ResourceTypeValue name, BuildingType buildingType) {
    this.name = name;
    this.buildingType = buildingType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ResourceTypeValue getName() {
    return name;
  }

  public void setName(ResourceTypeValue name) {
    this.name = name;
  }

  public BuildingType getBuildingType() {
    return buildingType;
  }

  public void setBuildingType(BuildingType buildingType) {
    this.buildingType = buildingType;
  }
}
