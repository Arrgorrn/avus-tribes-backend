package org.gfa.avustribesbackend.dtos;

import jakarta.persistence.Column;

public class KingdomResponseDTO {
  private final Long id;
  private final Long world;
  private final Long owner;
  private final String name;

    public KingdomResponseDTO(Long id, Long world, Long owner, String name) {
        this.id = id;
        this.world = world;
        this.owner = owner;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long getWorld() {
        return world;
    }

    public Long getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }
}
