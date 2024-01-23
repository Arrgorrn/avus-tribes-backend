package org.gfa.avustribesbackend.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WorldResponseDto {
  private long id;
  private String name;
  private long kingdom_count;

  @JsonCreator
  public WorldResponseDto(
      @JsonProperty("id") long id,
      @JsonProperty("name") String name,
      @JsonProperty("kingdom_count") long kingdom_count) {
    this.id = id;
    this.name = name;
    this.kingdom_count = kingdom_count;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getKingdom_count() {
    return kingdom_count;
  }

  public void setKingdom_count(long kingdom_count) {
    this.kingdom_count = kingdom_count;
  }
}
