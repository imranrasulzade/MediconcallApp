package com.matrix.mediconcallapp.model.dto.request;

import com.matrix.mediconcallapp.customValidation.annotation.ValidReservationStatus;
import com.matrix.mediconcallapp.enums.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationStatusDto {

    @NotNull(message = "id can not be null")
    private Integer id;

    @ValidReservationStatus(message = "reservation status can be PENDING, CONFIRMED, CANCELLED or COMPLETED")
    private ReservationStatus status;
}
