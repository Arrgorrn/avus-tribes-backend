package org.gfa.avustribesbackend.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Production {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "kingdom_id", nullable = false)
  private Kingdom kingdom;

  @OneToMany(mappedBy = "production")
  private List<Building> buildings;

  @OneToOne
  @JoinColumn(name = "resource_id", nullable = false)
  private Resource resource;

  @Column(nullable = false)
  private int amount;

  @Column(nullable = false)
  private boolean collected = false;

  @Column(name = "started_at", nullable = false)
  private LocalDateTime startedAt;

  @Column(name = "completed_at", nullable = false)
  private LocalDateTime completedAt;

  public Production() {}

  public Production(
      Kingdom kingdom,
      List<Building> buildings,
      Resource resource,
      int amount,
      boolean collected,
      LocalDateTime startedAt,
      LocalDateTime completedAt) {
    this.kingdom = kingdom;
    this.buildings = buildings;
    this.resource = resource;
    this.amount = amount;
    this.collected = collected;
    this.startedAt = startedAt;
    this.completedAt = completedAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Kingdom getKingdom() {
    return kingdom;
  }

  public void setKingdom(Kingdom kingdom) {
    this.kingdom = kingdom;
  }

  public List<Building> getBuildings() {
    return buildings;
  }

  public void setBuildings(List<Building> buildings) {
    this.buildings = buildings;
  }

  public Resource getResource() {
    return resource;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public boolean isCollected() {
    return collected;
  }

  public void setCollected(boolean collected) {
    this.collected = collected;
  }

  public LocalDateTime getStartedAt() {
    return startedAt;
  }

  public void setStartedAt(LocalDateTime startedAt) {
    this.startedAt = startedAt;
  }

  public LocalDateTime getCompletedAt() {
    return completedAt;
  }

  public void setCompletedAt(LocalDateTime completedAt) {
    this.completedAt = completedAt;
  }
}
