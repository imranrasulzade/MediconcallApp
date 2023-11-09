package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.MedicalRecord;
import com.matrix.mediconcallapp.enums.ReservationStatus;
import com.matrix.mediconcallapp.exception.child.DoctorNotFoundException;
import com.matrix.mediconcallapp.exception.child.MedicalRecordNotFoundException;
import com.matrix.mediconcallapp.exception.child.PatientNotFoundException;
import com.matrix.mediconcallapp.exception.child.ReservationNotFoundException;
import com.matrix.mediconcallapp.mapper.MedicalRecordMapper;
import com.matrix.mediconcallapp.model.dto.request.MedicalRecordReqDto;
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
    public void addMedicalRecord(HttpServletRequest request, MedicalRecordReqDto medicalRecordReqDto) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        Integer patientId = patientRepository.findById(medicalRecordReqDto.getPatientId())
                .orElseThrow(PatientNotFoundException::new).getId();
        Integer condition = reservationRepository
                .countByDoctorIdAndPatientIdAndStatus(doctorId, patientId, ReservationStatus.CONFIRMED)
                .orElse(0);
        if(condition > 0){
            medicalRecordReqDto.setDoctorId(doctorId);
            medicalRecordReqDto.setPatientId(patientId);
            medicalRecordReqDto.setTimestamp(LocalDateTime.now());
            MedicalRecord medicalRecord = medicalRecordMapper.toMedicalRecord(medicalRecordReqDto);
            medicalRecordRepository.save(medicalRecord);
        }else {
            throw new ReservationNotFoundException();
        }
    }

    @Override
    public List<MedicalRecordResp> getRecordsByDoctor(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        return medicalRecordRepository.findByDoctorIdAndStatus(doctorId, 1)
                .orElseThrow(MedicalRecordNotFoundException::new).stream()
                .map(medicalRecordMapper::toMedicalRecordResp).toList();
    }

    @Override
    public List<MedicalRecordResp> getRecordsByDoctorForPatient(HttpServletRequest request, Integer patientId) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        boolean condition = patientRepository.findById(patientId).isPresent();
        if(condition){
            return medicalRecordRepository.findByDoctorIdAndPatientIdAndStatus(doctorId, patientId, 1)
                    .orElseThrow(MedicalRecordNotFoundException::new).stream()
                    .map(medicalRecordMapper::toMedicalRecordResp).toList();

        }else {
            throw new PatientNotFoundException();
        }
    }

    @Override
    public List<MedicalRecordResp> getRecordsByPatient(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        return medicalRecordRepository.findByPatientIdAndStatus(patientId, 1)
                .orElseThrow(MedicalRecordNotFoundException::new).stream()
                .map(medicalRecordMapper::toMedicalRecordResp).toList();
    }

    @Override
    public List<MedicalRecordResp> getRecordsByPatientForDoctor(HttpServletRequest request, Integer doctorId) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        boolean condition = doctorRepository.findById(doctorId).isPresent();
        if(condition){
            return medicalRecordRepository.findByDoctorIdAndPatientIdAndStatus(doctorId, patientId, 1)
                    .orElseThrow(MedicalRecordNotFoundException::new).stream()
                    .map(medicalRecordMapper::toMedicalRecordResp).toList();

        }else {
            throw new DoctorNotFoundException();
        }
    }

    @Override
    public void deleteRecord(HttpServletRequest request, Integer id) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        MedicalRecord medicalRecord = medicalRecordRepository.findById(id)
                .orElseThrow(MedicalRecordNotFoundException::new);
        if(medicalRecord.getDoctor().getId().equals(doctorId)){
            medicalRecord.setStatus(-1);
            medicalRecordRepository.save(medicalRecord);
        } else {
            throw new MedicalRecordNotFoundException();
        }
    }

}
