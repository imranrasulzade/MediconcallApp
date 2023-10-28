package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.response.*;
import com.matrix.mediconcallapp.model.dto.request.*;
import com.matrix.mediconcallapp.service.AuthenticationService;
import com.matrix.mediconcallapp.service.DoctorService;
import com.matrix.mediconcallapp.service.UserService;
import com.matrix.mediconcallapp.service.impl.PasswordResetTokenService;
import com.matrix.mediconcallapp.service.PatientService;
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
    private final UserService userService;


    // hekim uchun registraciya
    @PostMapping(value = "/register/doctor", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DoctorDto> add(@ModelAttribute DoctorRegistrationRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(doctorService.add(requestDto));
    }


    // pasiyent ucun registraciya
    @PostMapping(value = "/register/patient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PatientDto> add(@ModelAttribute PatientRegistrationRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.add(requestDto));
    }


    // unudulan shifre uchun yenileme isteyi gondermek
    @PostMapping("/forgot-password")
    public ResponseEntity<Boolean> requestPasswordReset(@RequestParam String email){
        return tokenService.requestPasswordReset(email);
    }


    // token istifade ederek shifreni yenilemek
    @PatchMapping("/recovery-password")
    public ResponseEntity<Boolean> resetPassword(@RequestBody RecoveryPassword recoveryPassword) {
        return tokenService.resetPassword(recoveryPassword);
    }

    @ResponseBody
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginReq loginReq)  {
        return authenticationService.authenticate(loginReq);
    }


}
