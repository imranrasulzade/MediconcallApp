package com.matrix.mediconcallapp.model.dto.response;

import com.matrix.mediconcallapp.enums.ReservationStatus;
import lombok.Data;

@Data
public class ReservationDto {
    private Integer id;
    private Integer doctorId;
    private Integer patientId;
    private String doctorFullName;
    private String patientFullName;
    private String date;
    private ReservationStatus status;
}
