package com.matrix.mediconcallapp.model.dto.response;

import lombok.Data;


@Data
public class RatingRespDto {
    private Integer id;
    private Integer raterPatientId;
    private Integer ratedDoctorId;
    private String patientFullName;
    private String doctorFullName;
    private Integer ratingValue;
    private String comment;
    private String timestamp;
}
