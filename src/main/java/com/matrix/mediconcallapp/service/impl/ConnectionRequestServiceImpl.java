package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Request;
import com.matrix.mediconcallapp.exception.ConnectionNotFoundException;
import com.matrix.mediconcallapp.exception.NotFoundException;
import com.matrix.mediconcallapp.exception.UserNotFoundException;
import com.matrix.mediconcallapp.mapper.ConnectionMapper;
import com.matrix.mediconcallapp.model.dto.request.ConnectionRequestDto;
import com.matrix.mediconcallapp.repository.RequestRepository;
import com.matrix.mediconcallapp.service.ConnectionRequestService;
import com.matrix.mediconcallapp.service.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;

@Service
@RequiredArgsConstructor
public class ConnectionRequestServiceImpl implements ConnectionRequestService {
    private final RequestRepository requestRepository;
    private final ConnectionMapper connectionMapper;
    public final JwtUtil jwtUtil;

    @Override
    public void sendConnectionRequest(ConnectionRequestDto connectionRequestDto) {
        Request request = connectionMapper.toRequest(connectionRequestDto);
        requestRepository.save(request);
    }

    @Override
    public void acceptConnectionRequest(HttpServletRequest request, Integer patientId){
        Integer doctorId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        requestRepository.updateIsAccepted(doctorId, patientId);
    }

    @Override
    public void deleteConnectionByDoctor(HttpServletRequest request, Integer patientId){
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Request conn = requestRepository.findByDoctorAndPatient(userId, patientId)
                        .orElseThrow(ConnectionNotFoundException::new);
        requestRepository.delete(conn);
    }

    @Override
    public void deleteConnectionByPatient(HttpServletRequest request, Integer doctorId){
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Request conn = requestRepository.findByPatientAndDoctor(userId, doctorId)
                .orElseThrow(ConnectionNotFoundException::new);
        requestRepository.delete(conn);
    }
}
