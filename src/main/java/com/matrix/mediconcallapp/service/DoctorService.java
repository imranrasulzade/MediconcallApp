package com.matrix.mediconcallapp.service;


import com.matrix.mediconcallapp.model.dto.response.DoctorDto;
import com.matrix.mediconcallapp.model.dto.request.DoctorRegistrationRequestDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface DoctorService {
    DoctorDto getById(Integer id);
    DoctorDto getByIdFromHeader(HttpServletRequest request);

    List<DoctorDto> getAll();

    DoctorDto add(DoctorRegistrationRequestDto requestDto);

}
