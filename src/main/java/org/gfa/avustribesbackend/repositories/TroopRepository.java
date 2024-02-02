package org.gfa.avustribesbackend.repositories;

import org.gfa.avustribesbackend.models.Troop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TroopRepository extends JpaRepository<Troop, Long> {

}
