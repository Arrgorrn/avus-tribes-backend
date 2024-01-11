package org.gfa.avustribesbackend.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "players")
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Id", unique = true, nullable = false)
  private Long id;

  @Column(name = "username", unique = true, nullable = false)
  private String userName;
  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "verified_at")
  private Date verifiedAt;

  @Column(name = "verification_token", unique = true, nullable = false)
  private String verificationToken;

  @Column(name = "verification_token_expires_at", nullable = false)
  private Date verificationTokenExpiresAt;

  @Column(name = "forgotten_password_token", unique = true)
  private String forgottenPasswordToken;

  @Column(name = "forgotten_password_token_expires_at")
  private Date forgottenPasswordTokenExpiresAt;

  @Column(name = "created_at", nullable = false)
  private Date createdAt;

  @ManyToMany(mappedBy = "players")
  private List<World> worlds;

  public Player() {
    createdAt = new Date(System.currentTimeMillis());
  }

  public Player(
      String userName,
      String email,
      String password,
      String verificationToken) {
    this.userName = userName;
    this.email = email;
    this.password = password;
    this.verificationToken = verificationToken;
    this.verificationTokenExpiresAt = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
    this.createdAt = new Date(System.currentTimeMillis());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getVerifiedAt() {
    return verifiedAt;
  }

  public void setVerifiedAt(Date verifiedAt) {
    this.verifiedAt = verifiedAt;
  }

  public String getVerificationToken() {
    return verificationToken;
  }

  public void setVerificationToken(String verificationToken) {
    this.verificationToken = verificationToken;
  }

  public Date getVerificationTokenExpiresAt() {
    return verificationTokenExpiresAt;
  }

  public void setVerificationTokenExpiresAt(Date verificationTokenExpiresAt) {
    this.verificationTokenExpiresAt = verificationTokenExpiresAt;
  }

  public String getForgottenPasswordToken() {
    return forgottenPasswordToken;
  }

  public void setForgottenPasswordToken(String forgottenPasswordToken) {
    this.forgottenPasswordToken = forgottenPasswordToken;
  }

  public Date getForgottenPasswordTokenExpiresAt() {
    return forgottenPasswordTokenExpiresAt;
  }

  public void setForgottenPasswordTokenExpiresAt(Date forgottenPasswordTokenExpiresAt) {
    this.forgottenPasswordTokenExpiresAt = forgottenPasswordTokenExpiresAt;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public List<World> getWorlds() {
    return worlds;
  }

  public void setWorlds(List<World> worlds) {
    this.worlds = worlds;
  }
}
