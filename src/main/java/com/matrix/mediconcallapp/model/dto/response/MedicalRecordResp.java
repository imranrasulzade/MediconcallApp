package com.matrix.mediconcallapp.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class MedicalRecordResp {
    private Integer patientId;
    private Integer doctorId;
    private String diagnosis;
    private String treatment;
    private String documentPath;
    private LocalDateTime timestamp;
    private Integer status;
}
