package com.matrix.mediconcallapp.schedulers;

import com.matrix.mediconcallapp.entity.PasswordResetToken;
import com.matrix.mediconcallapp.entity.Reservation;
import com.matrix.mediconcallapp.enums.ReservationStatus;
import com.matrix.mediconcallapp.repository.PasswordResetTokenRepository;
import com.matrix.mediconcallapp.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ScheduledService {
    private final PasswordResetTokenRepository tokenRepository;
    private final ReservationRepository reservationRepository;
//    @Scheduled(fixedRate =10 * 60 * 1000) // 10 minute
    public void cleanupExpiredTokens() {
        Date now = new Date();
        List<PasswordResetToken> expiredTokens = tokenRepository.findByExpiryDateBefore(now);
        tokenRepository.deleteAll(expiredTokens);
        log.info("expired tokens deleted");
    }

//    @Scheduled(fixedRate =10 * 60 * 1000) // 10 minute
    public void cleanupCompletedReservation(){
        LocalDateTime now = LocalDateTime.now();
        List<Reservation> expiredReservations = reservationRepository
                .findByStatusOrDateBefore(ReservationStatus.COMPLETED, now);
        reservationRepository.deleteAll(expiredReservations);
        log.info("expired reservations deleted");
    }
}
