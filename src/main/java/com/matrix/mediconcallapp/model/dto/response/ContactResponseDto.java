package com.matrix.mediconcallapp.model.dto.response;

import lombok.Data;

@Data
public class ContactResponseDto {
    private Integer id;
    private Integer patientId;
    private String patientName;
    private Integer doctorId;
    private String doctorName;
    private Integer status;
    private Integer deletedByUser;

}
