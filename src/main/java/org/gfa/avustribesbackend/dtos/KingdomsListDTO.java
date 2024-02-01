package org.gfa.avustribesbackend.dtos;

import org.gfa.avustribesbackend.models.Kingdom;

import java.util.List;

public class KingdomsListDTO {
  private List<Kingdom> kingdoms;

  public KingdomsListDTO() {
  }

  public KingdomsListDTO(List<Kingdom> kingdoms) {
    this.kingdoms = kingdoms;
  }

  public List<Kingdom> getKingdoms() {
    return kingdoms;
  }

  public void setKingdoms(List<Kingdom> kingdoms) {
    this.kingdoms = kingdoms;
  }
}
