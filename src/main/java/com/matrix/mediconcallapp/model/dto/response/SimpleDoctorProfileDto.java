package com.matrix.mediconcallapp.model.dto.response;

import lombok.Data;

@Data
public class SimpleDoctorProfileDto {
    private Integer userId;
    private Integer doctorId;
    private String academicTitle;
    private String username;
    private String name;
    private String surname;
    private Double avgRating;
    private String specialty;
    private String qualification;
    private String info;
    private String photoUrl;
}
