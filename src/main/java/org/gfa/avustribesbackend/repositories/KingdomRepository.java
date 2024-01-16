package org.gfa.avustribesbackend.repositories;

import org.gfa.avustribesbackend.models.Kingdom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KingdomRepository extends JpaRepository<Kingdom, Long> {
}
