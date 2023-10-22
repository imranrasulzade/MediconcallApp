package com.matrix.mediconcallapp.repository;

import com.matrix.mediconcallapp.entity.Patient;
import com.matrix.mediconcallapp.model.projection.PatientProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    PatientProjection findPatientByUserId(Integer id);

}
