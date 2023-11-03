package com.matrix.mediconcallapp.repository;

import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<List<User>> findByStatus(UserStatus status);

    @Query("SELECT u FROM User u WHERE u.name LIKE :searchText% AND u.doctor IS NOT NULL")
    Optional<List<User>> findDoctorsByNameLike(String searchText);
}