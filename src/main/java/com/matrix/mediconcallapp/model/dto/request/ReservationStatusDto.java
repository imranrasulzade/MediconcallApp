package com.matrix.mediconcallapp.model.dto.request;

import com.matrix.mediconcallapp.enums.ReservationStatus;
import lombok.Data;

@Data
public class ReservationStatusDto {
    private Integer id;
    private ReservationStatus status;
}
