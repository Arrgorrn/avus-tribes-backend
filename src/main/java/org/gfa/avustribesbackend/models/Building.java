package org.gfa.avustribesbackend.models;

import jakarta.persistence.*;
import org.gfa.avustribesbackend.models.enums.BuildingTypeValue;

@Entity
public class Building {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", unique = true, nullable = false)
  private BuildingTypeValue type;

  @ManyToOne
  @JoinColumn(name = "kingdom_id", nullable = false)
  private Kingdom kingdom;

  private Integer level;

  public Building(BuildingTypeValue type, Kingdom kingdom) {
    this.type = type;
    this.kingdom = kingdom;
    this.level = 1;
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

  public BuildingTypeValue getType() {
    return type;
  }

  public void setType(BuildingTypeValue type) {
    this.type = type;
  }

  public Kingdom getKingdom() {
    return kingdom;
  }

  public void setKingdom(Kingdom kingdom) {
    this.kingdom = kingdom;
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
