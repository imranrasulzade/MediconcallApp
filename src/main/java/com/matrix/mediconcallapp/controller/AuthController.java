package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.request.*;
import com.matrix.mediconcallapp.service.AuthenticationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/account")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> requestPasswordReset(@RequestParam @NotBlank @Email String email){
        return authenticationService.requestPasswordReset(email);
    }


    @PatchMapping("/recovery-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid RecoveryPassword recoveryPassword) {
        return authenticationService.resetPassword(recoveryPassword);
    }


    @ResponseBody
    @PostMapping("sign-in")
    public ResponseEntity<?> login(@RequestBody @Valid LoginReq loginReq)  {
        return authenticationService.authenticate(loginReq);
    }


}
