package com.matrix.mediconcallapp.model.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ContactDto {
    @Schema(hidden = true)
    private Integer patientId;

    @NotBlank(message = "doctor id can not be empty or null")
    @Pattern(regexp = "^[0-9]+$", message = "Doctor ID must contain only digits")
    private Integer doctorId;

    @Schema(hidden = true)
    private Integer status;
}
