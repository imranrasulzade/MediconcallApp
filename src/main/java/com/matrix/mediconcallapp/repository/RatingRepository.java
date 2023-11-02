package com.matrix.mediconcallapp.repository;

import com.matrix.mediconcallapp.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    Optional<Integer> countByRaterPatientIdAndRatedDoctorId(Integer patientId, Integer doctorId);

    Optional<List<Rating>> findByRatedDoctorId(Integer doctorId);

    @Query(value = "select avg(rating_value) from rating where rated_doctor_id = :doctorId", nativeQuery = true)
    Optional<Double> findAverageRatingByDoctorId(@Param("doctorId") Integer doctorId);


}
