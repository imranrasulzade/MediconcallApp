package com.matrix.mediconcallapp.model.dto.request;


import com.matrix.mediconcallapp.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ReservationRequestDto {
    @Schema(hidden = true)
    private Integer id;

    @NotNull(message = "doctor id can not be empty or null")
    private Integer doctorId;

    @Schema(hidden = true)
    private Integer patientId;

    @NotNull(message = "Date and time must not be null")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:(00)$", message = "Date and time must be in the format yyyy-MM-dd HH:00")
    private String date;

    @Schema(hidden = true)
    private ReservationStatus status;

}
