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
}
