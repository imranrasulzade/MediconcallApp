package com.matrix.mediconcallapp.service;

import com.matrix.mediconcallapp.model.dto.request.ConnectionRequestDto;
import jakarta.servlet.http.HttpServletRequest;

import java.net.http.HttpRequest;

public interface ConnectionRequestService {
    void sendConnection(HttpServletRequest request, ConnectionRequestDto connectionRequestDto);

    void acceptConnection(HttpServletRequest request, Integer patientId);

    void deleteConnectionByDoctor(HttpServletRequest request, Integer patientId);

    void deleteConnectionByPatient(HttpServletRequest request, Integer doctorId);
}
