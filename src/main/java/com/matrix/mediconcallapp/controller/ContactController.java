package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.request.ContactDto;
import com.matrix.mediconcallapp.model.dto.response.ContactResponseDto;
import com.matrix.mediconcallapp.service.ContactService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;


    @GetMapping("/patient/contacts")
    public ResponseEntity<List<ContactResponseDto>> getAllForPatient(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(contactService.getAllForPatient(request));
    }


    @GetMapping("/doctor/contacts")
    public ResponseEntity<List<ContactResponseDto>> getAllForDoctor(HttpServletRequest request){
        return ResponseEntity.ok(contactService.getAllForDoctor(request));
    }

    @PostMapping("/patient/request")
    public ResponseEntity<Void> connect(HttpServletRequest request,
                                        @RequestBody @Valid ContactDto contactDto){
        contactService.send(request, contactDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PatchMapping("/doctor/accept")
    public ResponseEntity<Void> accept(HttpServletRequest request,
                                        @RequestBody @NotBlank @Pattern(regexp = "^[0-9]+$") Integer patientId){
        contactService.accept(request, patientId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @DeleteMapping("/doctor")
    public ResponseEntity<Void> deleteByDoctor(HttpServletRequest request,
                                               @RequestBody @NotBlank @Pattern(regexp = "^[0-9]+$") Integer patientId){
        contactService.deleteByDoctor(request, patientId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @DeleteMapping("/patient")
    public ResponseEntity<Void> deleteByPatient(HttpServletRequest request,
                                                @RequestBody @NotBlank @Pattern(regexp = "^[0-9]+$") Integer doctorId){
        contactService.deleteByPatient(request, doctorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
