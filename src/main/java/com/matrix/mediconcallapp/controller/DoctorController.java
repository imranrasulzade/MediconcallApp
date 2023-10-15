package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.response.DoctorDto;
import com.matrix.mediconcallapp.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping("/{id}")
    public DoctorDto getDoctorById(@PathVariable Integer id){
        return doctorService.getById(id);
    }

    @GetMapping("/")
    public List<DoctorDto> getAll(){
        return doctorService.getAll();
    }
}
