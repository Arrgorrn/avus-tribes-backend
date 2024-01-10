package org.gfa.avustribesbackend.services.ResetPassword;

import org.gfa.avustribesbackend.dtos.EmailDTO;
import org.springframework.http.ResponseEntity;

public interface ResetPasswordService {
    ResponseEntity<Object> resetPassword(EmailDTO email);
}
