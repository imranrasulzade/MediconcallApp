package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.exception.UserAlreadyExistsException;
import com.matrix.mediconcallapp.model.dto.response.*;
import com.matrix.mediconcallapp.model.dto.request.*;
import com.matrix.mediconcallapp.service.AuthenticationService;
import com.matrix.mediconcallapp.service.DoctorService;
import com.matrix.mediconcallapp.service.impl.PasswordResetTokenService;
import com.matrix.mediconcallapp.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/account")
public class AuthController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final PasswordResetTokenService tokenService;
    private final AuthenticationService authenticationService;


    // hekim uchun registraciya
    @PostMapping(value = "/register/doctor", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DoctorDto> add(@ModelAttribute @Valid DoctorRegistrationRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(doctorService.add(requestDto));
    }


    // pasiyent ucun registraciya
    @PostMapping(value = "/register/patient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PatientDto> add(@ModelAttribute @Valid PatientRegistrationRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.add(requestDto));
    }


    // unudulan shifre uchun yenileme isteyi gondermek
    @PostMapping("/forgot-password")
    public ResponseEntity<String> requestPasswordReset(@RequestParam @NotBlank @Email String email){
        return tokenService.requestPasswordReset(email);
    }


    // token istifade ederek shifreni yenilemek
    @PatchMapping("/recovery-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid RecoveryPassword recoveryPassword) {
        return tokenService.resetPassword(recoveryPassword);
    }

    @ResponseBody
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginReq loginReq)  {
        return authenticationService.authenticate(loginReq);
    }


}
