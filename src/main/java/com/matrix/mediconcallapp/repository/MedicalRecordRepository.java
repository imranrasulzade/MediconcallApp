package com.matrix.mediconcallapp.repository;

import com.matrix.mediconcallapp.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {

    Optional<List<MedicalRecord>> findByDoctorIdAndStatus(Integer id, Integer status);

    Optional<List<MedicalRecord>> findByPatientIdAndStatus(Integer id, Integer status);

    Optional<List<MedicalRecord>> findByDoctorIdAndPatientIdAndStatus(Integer doctorId, Integer patientId, Integer status);



}
