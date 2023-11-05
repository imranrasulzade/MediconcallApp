package com.matrix.mediconcallapp.repository;

import com.matrix.mediconcallapp.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Optional<List<Payment>> findByDoctorId(Integer id);
    Optional<List<Payment>> findByPatientId(Integer id);
    Optional<Payment> findByIdAndPatientId(Integer id, Integer patientId);
    Optional<Payment> findByIdAndDoctorId(Integer id, Integer doctorId);

}
