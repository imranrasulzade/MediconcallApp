package com.matrix.mediconcallapp.model.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MedicalRecordResp {
    private Integer patientId;
    private String patientName;
    private Integer doctorId;
    private String doctorName;
    private String diagnosis;
    private String treatment;
    private String documentPath;
    private LocalDateTime timestamp;
    private Integer status;
}
