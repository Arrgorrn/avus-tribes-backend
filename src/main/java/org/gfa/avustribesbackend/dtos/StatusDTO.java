package org.gfa.avustribesbackend.dtos;

public class StatusDTO {

    private String status;

    public StatusDTO(String status) {
        this.status = status;
    }

    public StatusDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
