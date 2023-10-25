package com.matrix.mediconcallapp.repository;

import com.matrix.mediconcallapp.entity.Reservation;
import com.matrix.mediconcallapp.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {


    @Query(value = "select * from reservation r where r.doctor_id=:id", nativeQuery = true)
    List<Reservation> findByDoctorId(@Param(value = "id") Integer id);


    @Query(value = "select * from reservation r where r.patient_id=:id", nativeQuery = true)
    List<Reservation> findByPatientId(@Param(value = "id") Integer id);


    //Isteyi qebul eden pasiyentin hekimin cedvellerine baxmasi
    @Query(value = "SELECT * FROM reservation WHERE doctor_id=:doctorId AND patient_id=:patientId", nativeQuery = true)
    List<Reservation> findByDoctorAndPatient(@Param(value = "doctorId") Integer doctorId,
                                             @Param(value = "patientId") Integer patientId);

    List<Reservation> findByStatusOrDateBefore(ReservationStatus status, LocalDateTime dateTime);

    List<Reservation> findByStatusAndDoctor_Id(ReservationStatus status, Integer id);
}


