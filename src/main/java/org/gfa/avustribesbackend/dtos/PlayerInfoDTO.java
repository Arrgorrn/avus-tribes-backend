package org.gfa.avustribesbackend.dtos;

import java.util.Date;

public class PlayerInfoDTO {
    private Long id;
    private String username;
    private boolean isVerified;
    private Date verified_at;

    public PlayerInfoDTO(Long id, String username, boolean isVerified, Date verified_at) {
        this.id = id;
        this.username = username;
        this.isVerified = isVerified;
        this.verified_at = verified_at;
    }

    public PlayerInfoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean verified) {
        isVerified = verified;
    }

    public Date getVerified_at() {
        return verified_at;
    }

    public void setVerified_at(Date verified_at) {
        this.verified_at = verified_at;
    }
}
