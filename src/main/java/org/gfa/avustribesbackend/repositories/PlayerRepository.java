package org.gfa.avustribesbackend.repositories;

import org.gfa.avustribesbackend.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

  Player findByVerificationToken(String token);
  boolean existsByUserName(String name);
  boolean existsByEmailIgnoreCase(String email);
  Player findByEmailIgnoreCase(String email);
  Player findByForgottenPasswordToken(String token);
}
