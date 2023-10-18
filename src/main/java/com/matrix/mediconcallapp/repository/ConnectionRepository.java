package com.matrix.mediconcallapp.repository;

import com.matrix.mediconcallapp.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Integer> {

    @Query(value = "SELECT * FROM connection WHERE doctor_id = (SELECT id FROM doctor WHERE user_id = ?1) AND patient_id = ?2", nativeQuery = true)
    Optional<Connection> findByDoctorAndPatient(Integer userId, Integer patientId);


    @Query(value = "SELECT * FROM connection WHERE patient_id = (SELECT id FROM patient WHERE user_id = ?1) AND doctor_id = ?2", nativeQuery = true)
    Optional<Connection> findByPatientAndDoctor(Integer userId, Integer doctorId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE connection SET is_accepted = 1 WHERE doctor_id = (SELECT id FROM doctor WHERE user_id = ?1) AND patient_id = ?2", nativeQuery = true)
    void updateIsAccepted(Integer doctorId, Integer patientId);




}
