package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.request.PatientEditReqDto;
import com.matrix.mediconcallapp.model.dto.request.PatientRegistrationRequestDto;
import com.matrix.mediconcallapp.model.dto.response.PatientDto;
import com.matrix.mediconcallapp.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;


    @GetMapping("/info")
    public ResponseEntity<PatientDto> get(HttpServletRequest request){
        return ResponseEntity.ok(patientService.get(request));
    }


    @PutMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(HttpServletRequest request,
                                       @ModelAttribute @Valid PatientEditReqDto editReqDto){
        patientService.update(request, editReqDto);
        return ResponseEntity.ok().build();
    }



    @GetMapping("doctor/patient/{id}")
    public ResponseEntity<PatientDto> getById(@PathVariable @Pattern(regexp = "^[0-9]+$") Integer id){
        return ResponseEntity.ok(patientService.getById(id));
    }



    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PatientDto> add(@ModelAttribute @Valid PatientRegistrationRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.add(requestDto));
    }
}
