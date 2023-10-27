package com.matrix.mediconcallapp.repository;

import com.matrix.mediconcallapp.entity.Reservation;
import com.matrix.mediconcallapp.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Optional<List<Reservation>> findByDoctorId(Integer id);

    Optional<List<Reservation>> findByPatientId(Integer id);

    List<Reservation> findByStatusOrDateBefore(ReservationStatus status, LocalDateTime dateTime);

    Optional<List<Reservation>> findByStatusAndDoctorId(ReservationStatus status, Integer id);
}


