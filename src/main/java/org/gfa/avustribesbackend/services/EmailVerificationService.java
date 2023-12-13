package org.gfa.avustribesbackend.services;

public interface EmailVerificationService {
boolean verifyEmail(String token);
void sendVerificationEmail(String email);
}
