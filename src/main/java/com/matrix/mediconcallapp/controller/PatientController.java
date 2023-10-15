package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.response.PatientDto;
import com.matrix.mediconcallapp.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/{id}")
    public PatientDto get(@PathVariable Integer id){
        return patientService.get(id);
    }

}
