package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.request.PatientEditReqDto;
import com.matrix.mediconcallapp.model.dto.request.RatingReqDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorForListProfileDto;
import com.matrix.mediconcallapp.model.dto.response.MedicalRecordResp;
import com.matrix.mediconcallapp.model.dto.response.PatientDto;
import com.matrix.mediconcallapp.model.dto.response.RatingRespDto;
import com.matrix.mediconcallapp.service.DoctorService;
import com.matrix.mediconcallapp.service.MedicalRecordService;
import com.matrix.mediconcallapp.service.PatientService;
import com.matrix.mediconcallapp.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final MedicalRecordService medicalRecordService;
    private final RatingService ratingService;

    @GetMapping("/info")
    public ResponseEntity<PatientDto> get(HttpServletRequest request){
        return ResponseEntity.ok(patientService.get(request));
    }


    @GetMapping("/records")
    public ResponseEntity<List<MedicalRecordResp>> getRecordsByPatient(HttpServletRequest request){
        return ResponseEntity.ok(medicalRecordService.getRecordsByPatient(request));
    }


    @GetMapping("/doctor-record")
    public ResponseEntity<List<MedicalRecordResp>> getRecordsByPatientForDoctor(HttpServletRequest request,
                                                                                @RequestParam Integer id){
        return ResponseEntity.ok(medicalRecordService.getRecordsByPatientForDoctor(request, id));
    }

    @PostMapping("/rate")
    public ResponseEntity<Void> addRating(HttpServletRequest request,
                                          @Valid @RequestBody RatingReqDto reqDto){
        ratingService.addRating(request, reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/search-doctors")
    public ResponseEntity<List<DoctorForListProfileDto>> getDoctorByName(@RequestParam String name){
        return ResponseEntity.ok(doctorService.getDoctorByName(name));
    }

    @GetMapping("/doctor")
    public ResponseEntity<?> getDoctorByIdForPatient(HttpServletRequest request,
                                                     @RequestParam Integer id){
        return doctorService.getDoctorByIdForPatient(request, id);
    }


    @PutMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> update(HttpServletRequest request,
                                       @ModelAttribute PatientEditReqDto editReqDto){
        patientService.update(request, editReqDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("comments/{id}")
    public ResponseEntity<List<RatingRespDto>> getRatingByDoctorId(@PathVariable Integer id){
        return ResponseEntity.ok(ratingService.getRatingByDoctorId(id));
    }
}
