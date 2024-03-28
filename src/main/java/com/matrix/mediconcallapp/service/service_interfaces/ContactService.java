package com.matrix.mediconcallapp.service.service_interfaces;

import com.matrix.mediconcallapp.model.dto.request.ContactDto;
import com.matrix.mediconcallapp.model.dto.response.ContactResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ContactService {
    void send(HttpServletRequest request, ContactDto contactDto);

    void accept(HttpServletRequest request, Integer patientId);

    void deleteByDoctor(HttpServletRequest request, Integer patientId);

    void deleteByPatient(HttpServletRequest request, Integer doctorId);

    List<ContactResponseDto> getAllForPatient(HttpServletRequest request);

    List<ContactResponseDto> getAllForDoctor(HttpServletRequest request);
}
