package com.matrix.mediconcallapp.schedulers;

import com.matrix.mediconcallapp.entity.PasswordResetToken;
import com.matrix.mediconcallapp.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ScheduledService {
    private final PasswordResetTokenRepository tokenRepository;
    @Scheduled(fixedRate =60 * 60 * 1000) // 60 minute
    public void cleanupExpiredTokens() {
        Date now = new Date();
        List<PasswordResetToken> expiredTokens = tokenRepository.findByExpiryDateBefore(now);
        tokenRepository.deleteAll(expiredTokens);
        log.info("expired tokens deleted");
    }
}
