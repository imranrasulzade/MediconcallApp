package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.request.ContactDto;
import com.matrix.mediconcallapp.model.dto.response.ContactResponseDto;
import com.matrix.mediconcallapp.service.service_interfaces.ContactService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/patient/contacts")
    public List<ContactResponseDto> getAllForPatient(HttpServletRequest request){
        return contactService.getAllForPatient(request);
    }


    @GetMapping("/doctor/contacts")
    public List<ContactResponseDto> getAllForDoctor(HttpServletRequest request){
        return contactService.getAllForDoctor(request);
    }

    @PostMapping("/patient/request")
    @ResponseStatus(HttpStatus.CREATED)
    public void connect(HttpServletRequest request,
                                        @RequestParam
                                        @NotBlank
                                        @Pattern(regexp = "^[0-9]+$") Integer doctorId){
        contactService.send(request, doctorId);

    }


    @PatchMapping("/doctor/accept")
    public void accept(HttpServletRequest request,
                                        @RequestBody
                                        @NotBlank
                                        @Pattern(regexp = "^[0-9]+$") Integer patientId){
        contactService.accept(request, patientId);
    }


    @DeleteMapping("/doctor")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByDoctor(HttpServletRequest request,
                                               @RequestParam
                                               @NotBlank
                                               @Pattern(regexp = "^[0-9]+$") Integer patientId){
        contactService.deleteByDoctor(request, patientId);
    }


    @DeleteMapping("/patient")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByPatient(HttpServletRequest request,
                                                @RequestParam
                                                @NotBlank
                                                @Pattern(regexp = "^[0-9]+$") Integer doctorId){
        contactService.deleteByPatient(request, doctorId);
    }

    @GetMapping("/patient/check")
    public Integer checkByPatient(HttpServletRequest request,
                         @RequestParam
                         @NotBlank
                         @Pattern(regexp = "^[0-9]+$") Integer doctorId){
        return contactService.checkByPatient(request, doctorId);
    }

}
