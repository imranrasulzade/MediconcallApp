package com.matrix.mediconcallapp.model.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContactDto {
    @Schema(hidden = true)
    private Integer patientId;

    @NotNull(message = "doctor id can not be null")
    private Integer doctorId;

    @Schema(hidden = true)
    private Integer status;
}
