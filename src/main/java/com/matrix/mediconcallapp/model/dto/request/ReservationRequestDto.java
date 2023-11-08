package com.matrix.mediconcallapp.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrix.mediconcallapp.enums.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ReservationRequestDto {
    @Schema(hidden = true)
    private Integer id;

    @NotNull(message = "doctor id can not be empty or null")
    private Integer doctorId;

    @Schema(hidden = true)
    private Integer patientId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Future(message = "Reservation time must be in the future")
    private LocalDateTime date;

    @Schema(hidden = true)
    private ReservationStatus status;

}
