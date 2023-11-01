package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.response.MedicalRecordResp;
import com.matrix.mediconcallapp.model.dto.response.PatientDto;
import com.matrix.mediconcallapp.service.MedicalRecordService;
import com.matrix.mediconcallapp.service.PatientService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;
    private final MedicalRecordService medicalRecordService;

    @GetMapping("/{id}")
    public PatientDto get(@PathVariable Integer id){
        return patientService.get(id);
    }


    @GetMapping("/records")
    public ResponseEntity<List<MedicalRecordResp>> getRecordsByPatient(HttpServletRequest request){
        return ResponseEntity.ok(medicalRecordService.getRecordsByPatient(request));
    }


    @GetMapping("doctor-record")
    public ResponseEntity<List<MedicalRecordResp>> getRecordsByPatientForDoctor(HttpServletRequest request,
                                                                                @RequestParam Integer id){
        return ResponseEntity.ok(medicalRecordService.getRecordsByPatientForDoctor(request, id));
    }
}
