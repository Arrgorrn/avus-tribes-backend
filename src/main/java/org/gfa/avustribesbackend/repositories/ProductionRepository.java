package org.gfa.avustribesbackend.repositories;

import org.gfa.avustribesbackend.models.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionRepository extends JpaRepository<Production, Long> {
}
