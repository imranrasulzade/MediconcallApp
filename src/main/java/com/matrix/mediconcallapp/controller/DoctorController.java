package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.request.DoctorEditReqDto;
import com.matrix.mediconcallapp.model.dto.request.MedicalRecordReqDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorDto;
import com.matrix.mediconcallapp.model.dto.response.MedicalRecordResp;
import com.matrix.mediconcallapp.model.dto.response.PatientDto;
import com.matrix.mediconcallapp.model.dto.response.RatingRespDto;
import com.matrix.mediconcallapp.service.DoctorService;
import com.matrix.mediconcallapp.service.MedicalRecordService;
import com.matrix.mediconcallapp.service.PatientService;
import com.matrix.mediconcallapp.service.RatingService;
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
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;
    private final MedicalRecordService medicalRecordService;
    private final RatingService ratingService;
    private final PatientService patientService;



    @GetMapping("/info")
    public ResponseEntity<DoctorDto> getDoctorById(HttpServletRequest request){
        return ResponseEntity.ok(doctorService.getByIdFromHeader(request));
    }



    @PostMapping(value = "new-medical-record", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addMedicalRecord(HttpServletRequest request,
                                                 @ModelAttribute @Valid MedicalRecordReqDto medicalRecordReqDto){
        medicalRecordService.addMedicalRecord(request, medicalRecordReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    @GetMapping("records")
    public ResponseEntity<List<MedicalRecordResp>> getRecordsByDoctor(HttpServletRequest request){
        return ResponseEntity.ok(medicalRecordService.getRecordsByDoctor(request));
    }



    @GetMapping("patient-record")
    public ResponseEntity<List<MedicalRecordResp>> getRecordsByDoctorForPatient(HttpServletRequest request,
                                                                 @RequestParam @NotBlank @Pattern(regexp = "^[0-9]+$")
                                                                 Integer id){
        return ResponseEntity.ok(medicalRecordService.getRecordsByDoctorForPatient(request, id));
    }



    @DeleteMapping("delete-record")
    public ResponseEntity<Void> deleteRecord(HttpServletRequest request,
                                             @RequestParam @NotBlank @Pattern(regexp = "^[0-9]+$") Integer id){
        medicalRecordService.deleteRecord(request, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



    @GetMapping("rating")
    public ResponseEntity<List<RatingRespDto>> getRating(HttpServletRequest request){
        return ResponseEntity.ok(ratingService.getRating(request));
    }



    @GetMapping("/patient/{id}")
    public ResponseEntity<PatientDto> getById(@PathVariable @Pattern(regexp = "^[0-9]+$") Integer id){
        return ResponseEntity.ok(patientService.getById(id));
    }


    @PutMapping("/edit")
    public ResponseEntity<Void> update(HttpServletRequest request,
                                       @RequestBody @Valid DoctorEditReqDto editReqDto){
        doctorService.update(request, editReqDto);
        return ResponseEntity.ok().build();
    }


}
