package com.matrix.mediconcallapp.service;


import com.matrix.mediconcallapp.model.dto.response.PatientDto;
import com.matrix.mediconcallapp.model.dto.request.PatientRegistrationRequestDto;

public interface PatientService {
    PatientDto add(PatientRegistrationRequestDto requestDto);

    PatientDto get(Integer id);
}
