package org.gfa.avustribesbackend.models;

import jakarta.persistence.*;

@Entity
public class BuildingType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Id", unique = true, nullable = false)
  private Long id;

  @Column(name = "Name", unique = true, nullable = false)
  private String name;

  @OneToOne
  @JoinColumn(name = "resource_type_id", nullable = true)
  private ResourceType resourceType;

  public BuildingType() {}

  public BuildingType(String name, ResourceType resourceType) {
    this.name = name;
    this.resourceType = resourceType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ResourceType getResourceType() {
    return resourceType;
  }

  public void setResourceType(ResourceType resourceType) {
    this.resourceType = resourceType;
  }
}
