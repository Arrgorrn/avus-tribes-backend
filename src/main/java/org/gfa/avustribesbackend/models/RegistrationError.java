package org.gfa.avustribesbackend.models;

public class RegistrationError {
  private String error;

  public RegistrationError(String error) {
    this.error = error;
  }

  public RegistrationError() {}

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }
}
