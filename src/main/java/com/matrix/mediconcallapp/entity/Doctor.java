package com.matrix.mediconcallapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

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

    @Size(max = 16)
    private String bankAccount;
    @NotNull(message = "status can not be null")
    private Integer status;
}
