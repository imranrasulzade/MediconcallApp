package com.matrix.mediconcallapp.repository;

import com.matrix.mediconcallapp.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Query(value = "SELECT * FROM contact WHERE doctor_id=:doctorId AND patient_id=:patientId", nativeQuery = true)
    Optional<Contact> findByDoctorAndPatient(@Param(value = "doctorId") Integer doctorId,
                                             @Param(value = "patientId") Integer patientId);

    @Query(value = "SELECT * FROM contact WHERE patient_id=:id AND status = 1", nativeQuery = true)
    List<Contact> findByPatient(@Param(value = "id") Integer id);

    @Query(value = "SELECT * FROM contact WHERE doctor_id=:id AND status <> -1", nativeQuery = true)
    List<Contact> findByDoctor(@Param(value = "id") Integer id);



}
