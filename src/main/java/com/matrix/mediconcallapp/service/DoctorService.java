package com.matrix.mediconcallapp.service;


import com.matrix.mediconcallapp.model.dto.request.DoctorEditReqDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorDto;
import com.matrix.mediconcallapp.model.dto.request.DoctorRegistrationRequestDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorForListProfileDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DoctorService {
    DoctorDto getById(Integer id);
    DoctorDto getByIdFromHeader(HttpServletRequest request);

    List<DoctorDto> getAll();

    DoctorDto add(DoctorRegistrationRequestDto requestDto);

    List<DoctorForListProfileDto> getDoctorByName(String name);

    ResponseEntity<?> getDoctorByIdForPatient(HttpServletRequest request, Integer id);

    void update(HttpServletRequest request, DoctorEditReqDto editReqDto);


}
