package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.entity.Rating;
import com.matrix.mediconcallapp.model.dto.request.MedicalRecordReqDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorDto;
import com.matrix.mediconcallapp.model.dto.response.MedicalRecordResp;
import com.matrix.mediconcallapp.model.dto.response.RatingRespDto;
import com.matrix.mediconcallapp.service.DoctorService;
import com.matrix.mediconcallapp.service.MedicalRecordService;
import com.matrix.mediconcallapp.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;
    private final MedicalRecordService medicalRecordService;
    private final RatingService ratingService;


    //headerden gelen userIdye gore oz datalarina baxmasi
    @GetMapping("/info")
    public DoctorDto getDoctorById(HttpServletRequest request){
        return doctorService.getByIdFromHeader(request);
    }



    //hekimin yeni record elave etmsei
    @PostMapping(value = "new-medical-record", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addMedicalRecord(HttpServletRequest request,
                                                 @ModelAttribute MedicalRecordReqDto medicalRecordReqDto){
        medicalRecordService.addMedicalRecord(request, medicalRecordReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //hekimin yazdigi butun recordlara baxmsi
    @GetMapping("records")
    public ResponseEntity<List<MedicalRecordResp>> getRecordsByDoctor(HttpServletRequest request){
        return ResponseEntity.ok(medicalRecordService.getRecordsByDoctor(request));
    }

    //hekimin secdiyi pasiyente yazdigi recordlara baxmasi
    @GetMapping("patient-record")
    public ResponseEntity<List<MedicalRecordResp>> getRecordsByDoctorForPatient(HttpServletRequest request,
                                                                 @RequestParam Integer id){
        return ResponseEntity.ok(medicalRecordService.getRecordsByDoctorForPatient(request, id));
    }

    //hekimin recordu silmeyi- status -1 olur.
    @DeleteMapping("delete-record")
    public ResponseEntity<Void> deleteRecord(HttpServletRequest request,
                                             @RequestParam Integer id){
        medicalRecordService.deleteRecord(request, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    //Ratinglere baxmasi
    @GetMapping("rating")
    public ResponseEntity<List<RatingRespDto>> getRating(HttpServletRequest request){
        return ResponseEntity.ok(ratingService.getRating(request));
    }


}
