package com.matrix.mediconcallapp.service;

import com.matrix.mediconcallapp.model.dto.request.MedicalRecordReqDto;
import com.matrix.mediconcallapp.model.dto.response.MedicalRecordResp;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicalRecordService {

    void addMedicalRecord(HttpServletRequest request, MedicalRecordReqDto medicalRecordReqDto);

    List<MedicalRecordResp> getRecordsByDoctor(HttpServletRequest request);

    List<MedicalRecordResp> getRecordsByDoctorForPatient(HttpServletRequest request, Integer patientId);

    List<MedicalRecordResp> getRecordsByPatient(HttpServletRequest request);

    List<MedicalRecordResp> getRecordsByPatientForDoctor(HttpServletRequest request, Integer doctorId);

    void deleteRecord(HttpServletRequest request, Integer id);

}
