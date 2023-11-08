package com.matrix.mediconcallapp.model.dto.request;

import com.matrix.mediconcallapp.customValidation.ValidReservationStatus;
import com.matrix.mediconcallapp.enums.ReservationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ReservationStatusDto {

    @NotBlank(message = "id can not be empty or null")
    @Pattern(regexp = "^[0-9]+$")
    private Integer id;

    @ValidReservationStatus(message = "reservation status can be PENDING, CONFIRMED, CANCELLED or COMPLETED")
    private ReservationStatus status;
}
