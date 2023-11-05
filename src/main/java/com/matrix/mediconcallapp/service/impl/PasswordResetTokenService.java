package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.PasswordResetToken;
import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.exception.PasswordMismatchException;
import com.matrix.mediconcallapp.model.dto.request.Email;
import com.matrix.mediconcallapp.model.dto.request.RecoveryPassword;
import com.matrix.mediconcallapp.repository.PasswordResetTokenRepository;
import com.matrix.mediconcallapp.service.EmailSenderService;
import com.matrix.mediconcallapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;


@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetTokenService {

    private final UserService userService;
    private final EmailSenderService emailSenderService;
    private final PasswordResetTokenRepository tokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found with this email!");
        }
        String token = generateRandomToken();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);
        Date expiryDate = calendar.getTime();
        createToken(user, token, expiryDate);

        Email receiverEmail = new Email();
        receiverEmail.setReceiver(email);
        receiverEmail.setText(token);
        receiverEmail.setSubject("Mediconcall - recovery password");
        try{
            emailSenderService.sendSimpleEmail(receiverEmail);
            log.info("token sent with email for recovery password to {}", email);
        }catch (Exception e){
            log.error("Error due to: {}", e.getMessage());
            ResponseEntity.badRequest().body("Email couldn't sent. Try again.");
        }

        return ResponseEntity.ok("Ok. Verify token was sent to your email");
    }

    public void createToken(User user, String token, Date expiryDate) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(expiryDate);
        tokenRepository.save(passwordResetToken);
    }

    public ResponseEntity<String> resetPassword(RecoveryPassword recoveryPassword) {
        if(recoveryPassword.getNewPassword().equals(recoveryPassword.getRetryPassword())){
            PasswordResetToken passwordResetToken = tokenRepository.findByToken(recoveryPassword.getToken());
            if (passwordResetToken == null || !isTokenValid(passwordResetToken)) {
                return ResponseEntity.badRequest().body("Ops! Something went wrong!");
            }
            User user = passwordResetToken.getUser();
            userService.changePassword(user, passwordEncoder.encode(recoveryPassword.getNewPassword()));
            deleteToken(passwordResetToken);

            return ResponseEntity.ok("Password reset successfully!");
        }else
            throw new PasswordMismatchException();
    }


    public boolean isTokenValid(PasswordResetToken token) {
        return token != null && !token.getExpiryDate().before(new Date());
    }

    public void deleteToken(PasswordResetToken token) {
        tokenRepository.delete(token);
    }

    private String generateRandomToken() {
        SecureRandom random = new SecureRandom();
        int TOKEN_LENGTH = 32;
        byte[] bytes = new byte[TOKEN_LENGTH / 2];
        random.nextBytes(bytes);
        return new BigInteger(1, bytes).toString(16);
    }

}

