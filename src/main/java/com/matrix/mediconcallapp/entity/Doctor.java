package com.matrix.mediconcallapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String specialty;
    private String academicTitle;
    private String qualification;
    private String placeOfWork;
    private String bankAccount;
    @NotNull(message = "status can not be null")
    private Integer status;
}
