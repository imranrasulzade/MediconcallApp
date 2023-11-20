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
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDto add(@ModelAttribute @Valid DoctorRegistrationRequestDto requestDto){
        return doctorService.add(requestDto);
    }


    @GetMapping("/info")
    public DoctorDto getDoctorById(HttpServletRequest request){
        return doctorService.getByIdFromHeader(request);
    }


    @PutMapping("/edit")
    public void update(HttpServletRequest request,
                                       @RequestBody @Valid DoctorEditReqDto editReqDto){
        doctorService.update(request, editReqDto);
    }


    @GetMapping("admin/doctor/{id}")
    public DoctorDto getDoctorById(@PathVariable @Valid Integer id){
        return doctorService.getById(id);
    }


    @GetMapping("admin/doctors")
    public List<DoctorDto> getAll(){
        return doctorService.getAll();
    }



    @GetMapping("patient/doctor-name")
    public List<DoctorForListProfileDto> getDoctorByName(@RequestParam @Size(max = 15)
                                                                         @NotBlank(message = "Name cannot be empty or null")
                                                                         @Pattern(regexp = "^[A-Za-z]+$") String name){
        return doctorService.getDoctorByName(name);
    }



    @GetMapping("/patient/doctor")
    public ResponseEntity<?> getDoctorByIdForPatient(HttpServletRequest request,
                                                     @RequestParam @NotBlank @Pattern(regexp = "^[0-9]+$") Integer id){
        return doctorService.getDoctorByIdForPatient(request, id);
    }

}
