package com.matrix.mediconcallapp.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PatientEditReqDto {
    @Schema(hidden = true)
    private Integer id;
    @Schema(hidden = true)
    private Integer userId;

    @NotNull
    private MultipartFile document;

    @NotNull
    private String bankAccount;

    @Schema(hidden = true)
    private Integer status = 1;
}
