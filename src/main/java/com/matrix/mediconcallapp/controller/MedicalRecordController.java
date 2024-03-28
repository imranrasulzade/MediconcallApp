package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.request.MedicalRecordReqDto;
import com.matrix.mediconcallapp.model.dto.response.MedicalRecordResp;
import com.matrix.mediconcallapp.service.service_interfaces.MedicalRecordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medical-record")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;


    @PostMapping(value = "doctor/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addMedicalRecord(HttpServletRequest request,
                                                 @ModelAttribute @Valid MedicalRecordReqDto medicalRecordReqDto){
        medicalRecordService.addMedicalRecord(request, medicalRecordReqDto);
    }



    @GetMapping("doctor/records")
    public List<MedicalRecordResp> getRecordsByDoctor(HttpServletRequest request){
        return medicalRecordService.getRecordsByDoctor(request);
    }



    @GetMapping("doctor/patient-record")
    public List<MedicalRecordResp> getRecordsByDoctorForPatient(HttpServletRequest request,
                                                                                @RequestParam @NotBlank @Pattern(regexp = "^[0-9]+$")
                                                                                Integer id){
        return medicalRecordService.getRecordsByDoctorForPatient(request, id);
    }



    @DeleteMapping("doctor/delete-record")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecord(HttpServletRequest request,
                                             @RequestParam @NotBlank @Pattern(regexp = "^[0-9]+$") Integer id){
        medicalRecordService.deleteRecord(request, id);
    }



    @GetMapping("/patient/records")
    public List<MedicalRecordResp> getRecordsByPatient(HttpServletRequest request){
        return medicalRecordService.getRecordsByPatient(request);
    }



    @GetMapping("/patient/doctor-record")
    public List<MedicalRecordResp> getRecordsByPatientForDoctor(HttpServletRequest request,
                                                                                @RequestParam @NotBlank @Pattern(regexp = "^[0-9]+$")
                                                                                Integer id){
        return medicalRecordService.getRecordsByPatientForDoctor(request, id);
    }



}
