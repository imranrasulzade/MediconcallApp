package com.matrix.mediconcallapp.service;

import com.matrix.mediconcallapp.model.dto.request.LoginReq;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    ResponseEntity<?> authenticate(LoginReq loginReq);
}
