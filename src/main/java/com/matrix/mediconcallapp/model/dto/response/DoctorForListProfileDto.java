package com.matrix.mediconcallapp.model.dto.response;

import lombok.Data;

@Data
public class DoctorForListProfileDto {
    private Integer userId;
    private Integer doctorId;
    private String academicTitle;
    private String name;
    private String surname;
    private String specialty;
    private String placeOfWork;
    private String info;
    private String photoUrl;
}
