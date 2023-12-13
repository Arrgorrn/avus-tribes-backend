package org.gfa.avustribesbackend.models;

import jakarta.persistence.*;

@Entity
public class Resource {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(mappedBy = "resource")
  private Production production;

  @ManyToOne
  @JoinColumn(name = "kingdom_id", nullable = false)
  private Kingdom kingdom;

  private int amount;

  public Resource() {
    this.amount = 0;
  }

  public Resource(Long id, Production production, Kingdom kingdom) {
    this.id = id;
    this.production = production;
    this.kingdom = kingdom;
    this.amount = 0;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Production getProduction() {
    return production;
  }

  public void setProduction(Production production) {
    this.production = production;
  }

  public Kingdom getKingdom() {
    return kingdom;
  }

  public void setKingdom(Kingdom kingdom) {
    this.kingdom = kingdom;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Amount cannot be negative");
    }
    this.amount = amount;
  }
}
