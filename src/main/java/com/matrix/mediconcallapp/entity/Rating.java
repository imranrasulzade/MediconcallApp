package com.matrix.mediconcallapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.Data;

import java.time.LocalDateTime;

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

    @Max(5)
    private Integer ratingValue;
    private String comment;
    private LocalDateTime timestamp;
    private Integer status;
}
