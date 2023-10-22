package com.matrix.mediconcallapp.model.dto.response;

import lombok.Data;

@Data
public class ContactResponseDto {
    private Integer id;
    private Integer patientId;
    private Integer doctorId;
    private Integer status;
    private Integer deletedByUser;

}
