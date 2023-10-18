package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Connection;
import com.matrix.mediconcallapp.entity.Patient;
import com.matrix.mediconcallapp.exception.ConnectionNotFoundException;
import com.matrix.mediconcallapp.exception.PatientNotFoundException;
import com.matrix.mediconcallapp.mapper.ConnectionMapper;
import com.matrix.mediconcallapp.model.dto.request.ConnectionRequestDto;
import com.matrix.mediconcallapp.repository.ConnectionRepository;
import com.matrix.mediconcallapp.repository.PatientRepository;
import com.matrix.mediconcallapp.service.ConnectionRequestService;
import com.matrix.mediconcallapp.service.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConnectionRequestServiceImpl implements ConnectionRequestService {
    private final ConnectionRepository connectionRepository;
    private final ConnectionMapper connectionMapper;
    private final JwtUtil jwtUtil;
    private final PatientRepository patientRepository;

    @Override
    public void sendConnection(HttpServletRequest request, ConnectionRequestDto connectionRequestDto) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Patient patient = patientRepository.findByUser(userId).orElseThrow(PatientNotFoundException::new);
        connectionRequestDto.setPatientId(patient.getId());
        Connection connection = connectionMapper.toConnection(connectionRequestDto);
        connectionRepository.save(connection);
    }

    @Override
    public void acceptConnection(HttpServletRequest request, Integer patientId){
        Integer doctorId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        connectionRepository.updateIsAccepted(doctorId, patientId);
    }

    @Override
    public void deleteConnectionByDoctor(HttpServletRequest request, Integer patientId){
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Connection conn = connectionRepository.findByDoctorAndPatient(userId, patientId)
                        .orElseThrow(ConnectionNotFoundException::new);
        connectionRepository.delete(conn);
    }

    @Override
    public void deleteConnectionByPatient(HttpServletRequest request, Integer doctorId){
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Connection conn = connectionRepository.findByPatientAndDoctor(userId, doctorId)
                .orElseThrow(ConnectionNotFoundException::new);
        connectionRepository.delete(conn);
    }
}
