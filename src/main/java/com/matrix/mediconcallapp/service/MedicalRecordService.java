package com.matrix.mediconcallapp.service;

import com.matrix.mediconcallapp.entity.MedicalRecord;
import com.matrix.mediconcallapp.model.dto.request.MedicalRecordDtoReq;
import com.matrix.mediconcallapp.model.dto.response.MedicalRecordResp;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedicalRecordService {

    void addMedicalRecord(HttpServletRequest request, MedicalRecordDtoReq medicalRecordDtoReq);

    List<MedicalRecordResp> getRecords(HttpServletRequest reques);
}
