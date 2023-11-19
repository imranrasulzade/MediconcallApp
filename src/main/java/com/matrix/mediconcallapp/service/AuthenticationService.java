package com.matrix.mediconcallapp.service;

import com.matrix.mediconcallapp.model.dto.request.LoginReq;
import com.matrix.mediconcallapp.model.dto.request.RecoveryPassword;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    ResponseEntity<?> authenticate(LoginReq loginReq);

    ResponseEntity<String> requestPasswordReset(String email);

    ResponseEntity<String> resetPassword(RecoveryPassword recoveryPassword);
}
