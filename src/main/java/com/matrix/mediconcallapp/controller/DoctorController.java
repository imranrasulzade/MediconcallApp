package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.request.DoctorEditReqDto;
import com.matrix.mediconcallapp.model.dto.request.DoctorRegistrationRequestDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorForListProfileDto;
import com.matrix.mediconcallapp.service.DoctorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;


    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DoctorDto> add(@ModelAttribute @Valid DoctorRegistrationRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(doctorService.add(requestDto));
    }


    @GetMapping("/info")
    public ResponseEntity<DoctorDto> getDoctorById(HttpServletRequest request){
        return ResponseEntity.ok(doctorService.getByIdFromHeader(request));
    }


    @PutMapping("/edit")
    public ResponseEntity<Void> update(HttpServletRequest request,
                                       @RequestBody @Valid DoctorEditReqDto editReqDto){
        doctorService.update(request, editReqDto);
        return ResponseEntity.ok().build();
    }


    @GetMapping("admin/doctor/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable @Valid Integer id){
        return ResponseEntity.ok(doctorService.getById(id));
    }


    @GetMapping("admin/doctors")
    public ResponseEntity<List<DoctorDto>> getAll(){
        return ResponseEntity.ok(doctorService.getAll());
    }



    @GetMapping("patient/doctor-name")
    public ResponseEntity<List<DoctorForListProfileDto>> getDoctorByName(@RequestParam @Size(max = 15)
                                                                         @NotBlank(message = "Name cannot be empty or null")
                                                                         @Pattern(regexp = "^[A-Za-z]+$") String name){
        return ResponseEntity.ok(doctorService.getDoctorByName(name));
    }



    @GetMapping("/patient/doctor")
    public ResponseEntity<?> getDoctorByIdForPatient(HttpServletRequest request,
                                                     @RequestParam @NotBlank @Pattern(regexp = "^[0-9]+$") Integer id){
        return doctorService.getDoctorByIdForPatient(request, id);
    }

}
