package com.matrix.mediconcallapp.repository;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.model.projection.DoctorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    DoctorProjection findDoctorByUserId(Integer id);

    Optional<Doctor> findByUserId(Integer id);


}
