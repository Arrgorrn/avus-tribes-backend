package org.gfa.avustribesbackend.models;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Entity
@Table(name = "email_verifications")
public class EmailVerification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true, nullable = false)
  private Long id;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "player_id", nullable = false)
  private Player player;
  @Column(name = "token", unique = true, nullable = false)
  private String token;
  @Column(name = "created_at", nullable = false)
  private Date createdAt;
  @Column(name = "expires_at", nullable = false)
  private Date expiresAt;
//  @Transient
//  @Value("${VERIFICATION_EXPIRATION_TIME}")
//  private String VERIFICATION_EXPIRATION_TIME;

  public EmailVerification() {
  }

  public EmailVerification(String token, Player player) {
    this.player = player;
    this.token = token;
    this.createdAt = new Date(System.currentTimeMillis());
    this.expiresAt = new Date(System.currentTimeMillis() + 3600000 /*Long.parseLong(VERIFICATION_EXPIRATION_TIME)*/);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Date expiresAt) {
    this.expiresAt = expiresAt;
  }
}
