package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.MedicalRecord;
import com.matrix.mediconcallapp.enums.ReservationStatus;
import com.matrix.mediconcallapp.exception.MedicalRecordException;
import com.matrix.mediconcallapp.exception.PatientNotFoundException;
import com.matrix.mediconcallapp.exception.ReservationNotFoundException;
import com.matrix.mediconcallapp.mapper.MedicalRecordMapper;
import com.matrix.mediconcallapp.model.dto.request.MedicalRecordDtoReq;
import com.matrix.mediconcallapp.model.dto.response.MedicalRecordResp;
import com.matrix.mediconcallapp.repository.DoctorRepository;
import com.matrix.mediconcallapp.repository.MedicalRecordRepository;
import com.matrix.mediconcallapp.repository.PatientRepository;
import com.matrix.mediconcallapp.repository.ReservationRepository;
import com.matrix.mediconcallapp.service.MedicalRecordService;
import com.matrix.mediconcallapp.service.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final JwtUtil jwtUtil;
    private final MedicalRecordRepository medicalRecordRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final MedicalRecordMapper medicalRecordMapper;
    private final ReservationRepository reservationRepository;


    @Override
    public void addMedicalRecord(HttpServletRequest request, MedicalRecordDtoReq medicalRecordDtoReq) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        Integer patientId = patientRepository.findById(medicalRecordDtoReq.getPatientId())
                .orElseThrow(PatientNotFoundException::new).getId();
        boolean condition = reservationRepository
                .findByDoctorIdAndPatientIdAndStatus(doctorId, patientId, ReservationStatus.CONFIRMED)
                .stream().toList().isEmpty();
        if(!condition){
            medicalRecordDtoReq.setDoctorId(doctorId);
            medicalRecordDtoReq.setPatientId(patientId);
            medicalRecordDtoReq.setTimestamp(LocalDateTime.now());
            MedicalRecord medicalRecord = medicalRecordMapper.toMedicalRecord(medicalRecordDtoReq);
            medicalRecordRepository.save(medicalRecord);
        }else {
            throw new ReservationNotFoundException();
        }
    }

    @Override
    public List<MedicalRecordResp> getRecords(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        return medicalRecordRepository.findByDoctorId(doctorId)
                .orElseThrow(MedicalRecordException::new).stream()
                .map(medicalRecordMapper::toMedicalRecordResp).toList();
    }
}
