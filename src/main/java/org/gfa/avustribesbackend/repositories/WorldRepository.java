package org.gfa.avustribesbackend.repositories;

import org.gfa.avustribesbackend.models.World;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorldRepository extends JpaRepository<World, Long> {
}
