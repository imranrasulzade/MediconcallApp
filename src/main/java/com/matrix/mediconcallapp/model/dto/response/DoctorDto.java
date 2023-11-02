package com.matrix.mediconcallapp.model.dto.response;

import lombok.Data;

@Data
public class DoctorDto {
    private Integer doctorId;
    private Integer userId;
    private String name;
    private String surname;
    private String academicTitle;
    private String specialty;
    private String placeOfWork;
    private String phone;
    private String qualification;
    private Double avgRating;
    private String info;
    private Integer status;
}
