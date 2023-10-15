package com.matrix.mediconcallapp.repository;

import com.matrix.mediconcallapp.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

    List<PasswordResetToken> findByExpiryDateBefore(Date date);
}
