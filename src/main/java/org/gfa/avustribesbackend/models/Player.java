package org.gfa.avustribesbackend.models;

import jakarta.persistence.*;
import org.gfa.avustribesbackend.models.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "players")
public class Player implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Id", unique = true, nullable = false)
  private Long id;

  @Column(name = "playerName", unique = true, nullable = false)
  private String playerName;
  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "verified_at")
  private Date verifiedAt;

  @Column(name = "is_verified")
  private Boolean isVerified;

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

  @Enumerated(EnumType.STRING)
  private Role role;

  public Player() {
    createdAt = new Date(System.currentTimeMillis());
    this.isVerified = false;
  }

  public Player(
      String playerName,
      String email,
      String password,
      String verificationToken) {
    this.playerName = playerName;
    this.email = email;
    this.password = password;
    this.isVerified = false;
    this.verificationToken = verificationToken;
    this.verificationTokenExpiresAt = new Date(System.currentTimeMillis() + 1000 * 60 * 60);
    this.createdAt = new Date(System.currentTimeMillis());
    this.role = Role.USER;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String userName) {
    this.playerName = userName;
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

  public Boolean getIsVerified() {
    return isVerified;
  }

  public void setIsVerified(Boolean verified) {
    isVerified = verified;
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

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
