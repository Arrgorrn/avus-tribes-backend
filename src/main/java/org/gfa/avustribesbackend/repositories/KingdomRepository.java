package org.gfa.avustribesbackend.repositories;

import org.gfa.avustribesbackend.models.Kingdom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KingdomRepository extends JpaRepository <Kingdom, Long> {
}
