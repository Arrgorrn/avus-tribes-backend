package org.gfa.avustribesbackend.models;

import jakarta.persistence.*;
import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;

@Entity
public class BuildingType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Id", unique = true, nullable = false)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "Name", unique = true, nullable = false)
  private BuildingTypeValue name;

  @OneToOne
  @JoinColumn(name = "resource_type_id", nullable = true)
  private ResourceType resourceType;

  public BuildingType() {}

  public BuildingType(BuildingTypeValue name, ResourceType resourceType) {
    this.name = name;
    this.resourceType = resourceType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BuildingTypeValue getName() {
    return name;
  }

  public void setName(BuildingTypeValue name) {
    this.name = name;
  }

  public ResourceType getResourceType() {
    return resourceType;
  }

  public void setResourceType(ResourceType resourceType) {
    this.resourceType = resourceType;
  }
}
