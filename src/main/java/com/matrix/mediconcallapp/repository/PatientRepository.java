package com.matrix.mediconcallapp.repository;

import com.matrix.mediconcallapp.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    @Query(value = "select * from patient where user_id = ?1", nativeQuery = true)
    Optional<Patient> findByUser(Integer userId);
}
