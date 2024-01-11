package org.gfa.avustribesbackend.services.ResetPassword;

import org.gfa.avustribesbackend.dtos.EmailRequestDTO;
import org.gfa.avustribesbackend.dtos.PasswordRequestDTO;
import org.gfa.avustribesbackend.dtos.TokenRequestDTO;
import org.springframework.http.ResponseEntity;

public interface ResetPasswordService {

  ResponseEntity<Object> sendResetPasswordEmail(EmailRequestDTO email);

  ResponseEntity<Object> resetPassword(TokenRequestDTO token, PasswordRequestDTO newPassword);
}
