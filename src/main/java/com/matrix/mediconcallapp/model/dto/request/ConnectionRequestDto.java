package com.matrix.mediconcallapp.model.dto.request;


import lombok.Data;

@Data
public class ConnectionRequestDto {
    private Integer id;
    private Integer patientId;

    private Integer doctorId;
    private Integer isAccepted;
}
