package com.matrix.mediconcallapp.model.dto.response;

import lombok.Data;

@Data
public class PatientDto {
    private Integer id;
    private String name;
    private String surname;
    private String phone;
    private String info;
    private String documentPath;
    private Integer status;
}
