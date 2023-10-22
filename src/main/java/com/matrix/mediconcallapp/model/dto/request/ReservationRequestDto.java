package com.matrix.mediconcallapp.model.dto.request;

import com.matrix.mediconcallapp.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ReservationRequestDto {
    @Schema(hidden = true)
    Integer id;

    Integer doctorId;

    @Schema(hidden = true)
    Integer patientId;

    @NotNull(message = "Date and time must not be null")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$", message = "Date and time must be in the format yyyy-MM-dd HH:mm")
    String date;

    @Schema(hidden = true)
    ReservationStatus status;

}
