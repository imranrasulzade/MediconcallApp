package com.matrix.mediconcallapp.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Doctor doctor;
    private Integer amount;
    private Integer status;
}
