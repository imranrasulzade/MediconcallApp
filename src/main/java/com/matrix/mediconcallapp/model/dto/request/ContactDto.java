package com.matrix.mediconcallapp.model.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ContactDto {
    @Schema(hidden = true)
    private Integer patientId;
    private Integer doctorId;

    @Schema(hidden = true)
    private Integer status;
}