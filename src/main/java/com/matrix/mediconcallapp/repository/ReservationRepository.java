package com.matrix.mediconcallapp.repository;

import com.matrix.mediconcallapp.entity.Doctor;
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

    @Query(value = "select * from reservation r where r.patient_id=(select id from patient where user_id=:id)", nativeQuery = true)
    List<Reservation> findByPatientId(@Param(value = "id") Integer id);


    //Isteyi qebul eden pasiyentin hekimin cedvellerine baxmasi
    @Query(value = "SELECT * FROM reservation WHERE doctor_id=:doctorId AND patient_id=:patientId", nativeQuery = true)
    List<Reservation> findByDoctorAndPatient(@Param(value = "doctorId") Integer doctorId,
                                             @Param(value = "patientId") Integer patientId);
}
