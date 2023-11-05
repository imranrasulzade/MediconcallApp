package com.matrix.mediconcallapp.service;


import com.matrix.mediconcallapp.model.dto.request.PatientEditReqDto;
import com.matrix.mediconcallapp.model.dto.response.PatientDto;
import com.matrix.mediconcallapp.model.dto.request.PatientRegistrationRequestDto;
import jakarta.servlet.http.HttpServletRequest;

public interface PatientService {
    PatientDto add(PatientRegistrationRequestDto requestDto);

    PatientDto get(HttpServletRequest request);
    PatientDto getById(Integer patientId);

    void update(HttpServletRequest request, PatientEditReqDto editReqDto);
}
