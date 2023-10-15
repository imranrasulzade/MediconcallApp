package com.matrix.mediconcallapp.repository;

import com.matrix.mediconcallapp.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query(value = "select * from  reservation r where r.doctor_id=:id", nativeQuery = true)
    List<Reservation> findByDoctorId(@Param(value = "id") Integer id);

    @Query(value = "select * from reservation r where r.patient_id=:id", nativeQuery = true)
    List<Reservation> findByPatientId(@Param(value = "id") Integer id);


    //Isteyi qebul eden pasiyentin hekimin cedvellerine baxmasi
    @Query(value = "SELECT r.id, r.doctor_id, r.patient_id, r.date, r.is_accepted, r.status " +
            "FROM request AS rq " +
            "INNER JOIN reservation AS r ON rq.doctor_id = r.doctor_id " +
            "WHERE rq.is_accepted = 1 AND rq.patient_id = ?1 AND r.doctor_id = ?2", nativeQuery = true)
    List<Reservation> findByDoctorIdForIsAcceptedPatient(Integer patientId, Integer doctorId);
}
