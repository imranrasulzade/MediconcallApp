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

    Optional<Contact> findByDoctorIdAndPatientId(Integer doctorId, Integer patientId);

    List<Contact> findByPatientIdAndStatus(Integer id, Integer status);

    @Query(value = "SELECT * FROM contact WHERE doctor_id=:id AND status <> -1", nativeQuery = true)
    List<Contact> findByDoctor(@Param(value = "id") Integer id);


    @Query(value = "SELECT * FROM contact WHERE doctor_id=:doctorId AND patient_id=:patientId AND status=1", nativeQuery = true)
    Optional<Contact> findAcceptedContact(@Param(value = "doctorId") Integer doctorId,
                                             @Param(value = "patientId") Integer patientId);

    Optional<Integer> countByDoctorIdAndPatientIdAndStatus(Integer doctorId, Integer patientId, Integer status);


}
