package org.gfa.avustribesbackend.services.Email;

public interface EmailVerificationService {
boolean verifyEmail(String token);
void sendVerificationEmail(String email);
}
