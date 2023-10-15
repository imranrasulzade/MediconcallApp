package com.matrix.mediconcallapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "authorities")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    private String name;
}
