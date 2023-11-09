package com.matrix.mediconcallapp.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class MedicalRecordReqDto {

    @NotNull(message = "patient id can not be null")
    private Integer patientId;

    @Schema(hidden = true)
    private Integer doctorId;


    private String diagnosis;
    private String treatment;

    @NotNull(message = "document can not be null")
    private MultipartFile document;

    @Schema(hidden = true)
    private LocalDateTime timestamp;

    @Schema(hidden = true)
    private Integer status = 1;
}
