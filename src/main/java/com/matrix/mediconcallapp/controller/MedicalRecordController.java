package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.request.MedicalRecordReqDto;
import com.matrix.mediconcallapp.model.dto.response.MedicalRecordResp;
import com.matrix.mediconcallapp.service.MedicalRecordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medical-record")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;


    @PostMapping(value = "doctor/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addMedicalRecord(HttpServletRequest request,
                                                 @ModelAttribute @Valid MedicalRecordReqDto medicalRecordReqDto){
        medicalRecordService.addMedicalRecord(request, medicalRecordReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    @GetMapping("doctor/records")
    public ResponseEntity<List<MedicalRecordResp>> getRecordsByDoctor(HttpServletRequest request){
        return ResponseEntity.ok(medicalRecordService.getRecordsByDoctor(request));
    }



    @GetMapping("doctor/patient-record")
    public ResponseEntity<List<MedicalRecordResp>> getRecordsByDoctorForPatient(HttpServletRequest request,
                                                                                @RequestParam @NotBlank @Pattern(regexp = "^[0-9]+$")
                                                                                Integer id){
        return ResponseEntity.ok(medicalRecordService.getRecordsByDoctorForPatient(request, id));
    }



    @DeleteMapping("doctor/delete-record")
    public ResponseEntity<Void> deleteRecord(HttpServletRequest request,
                                             @RequestParam @NotBlank @Pattern(regexp = "^[0-9]+$") Integer id){
        medicalRecordService.deleteRecord(request, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



    @GetMapping("/patient/records")
    public ResponseEntity<List<MedicalRecordResp>> getRecordsByPatient(HttpServletRequest request){
        return ResponseEntity.ok(medicalRecordService.getRecordsByPatient(request));
    }



    @GetMapping("/patient/doctor-record")
    public ResponseEntity<List<MedicalRecordResp>> getRecordsByPatientForDoctor(HttpServletRequest request,
                                                                                @RequestParam @NotBlank @Pattern(regexp = "^[0-9]+$")
                                                                                Integer id){
        return ResponseEntity.ok(medicalRecordService.getRecordsByPatientForDoctor(request, id));
    }



}
