package com.matrix.mediconcallapp.model.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class DoctorProfileDto {
    private Integer userId;
    private Integer doctorId;
    private String username;
    private String specialty;
    private String academicTitle;
    private String name;
    private String surname;
    private Double avgRating;
    private String qualification;
    private String email;
    private String phone;
    private String placeOfWork;
    private Date birthday;
    private Integer gender;
    private String address;
    private String info;
    private String photoUrl;
}
