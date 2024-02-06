package org.gfa.avustribesbackend.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "password_resets")
public class PasswordReset {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "player_id", nullable = false)
  private Player player;
  @Column(name = "token", unique = true, nullable = false)
  private String token;
  @Column(name = "expires_at", nullable = false)
  private Date expiresAt;
  @Column(name = "created_at", nullable = false)
  private Date createdAt;
}
