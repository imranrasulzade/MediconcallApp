package com.matrix.mediconcallapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rater_patient_id")
    private Patient raterPatient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rated_doctor_id")
    private Doctor ratedDoctor;

    private Integer ratingValue;
    private String comment;
    private Date timestamp;
    private Integer status;
}
