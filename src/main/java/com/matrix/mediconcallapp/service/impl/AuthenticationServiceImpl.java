package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.PasswordResetToken;
import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.exception.child.PasswordMismatchException;
import com.matrix.mediconcallapp.model.dto.request.Email;
import com.matrix.mediconcallapp.model.dto.request.LoginReq;
import com.matrix.mediconcallapp.model.dto.request.RecoveryPassword;
import com.matrix.mediconcallapp.model.dto.response.ErrorRes;
import com.matrix.mediconcallapp.model.dto.response.LoginRes;
import com.matrix.mediconcallapp.repository.PasswordResetTokenRepository;
import com.matrix.mediconcallapp.service.AuthenticationService;
import com.matrix.mediconcallapp.service.EmailSenderService;
import com.matrix.mediconcallapp.service.UserService;
import com.matrix.mediconcallapp.service.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final EmailSenderService emailSenderService;
    private final PasswordResetTokenRepository tokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> authenticate(LoginReq loginReq){
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getUsername(),
                            loginReq.getPassword()));
            log.info("authentication details: {}", authentication);
            String username = authentication.getName();
            User user = new User(username,"");
            String token = jwtUtil.createToken(user);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            LoginRes loginRes = new LoginRes(username,token);
            log.info("user: {} logged in",  user.getUsername());
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(loginRes);

        }catch (BadCredentialsException e){
            log.error("Error due to {} ", e.getMessage());
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            log.error("Error due to {} ", e.getMessage());
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


    @Override
    public ResponseEntity<String> requestPasswordReset(String email) {
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
            return ResponseEntity.badRequest().body("Email couldn't sent. Try again.");
        }

        return ResponseEntity.ok("Ok. Verify token was sent to your email");
    }


    @Override
    public ResponseEntity<String> resetPassword(RecoveryPassword recoveryPassword) {
        if(recoveryPassword.getNewPassword().equals(recoveryPassword.getRetryPassword())){
            PasswordResetToken passwordResetToken = tokenRepository.findByToken(recoveryPassword.getToken());
            if (!isTokenValid(passwordResetToken)) {
                return ResponseEntity.badRequest().body("Ops! Something went wrong!");
            }
            User user = passwordResetToken.getUser();
            userService.changePassword(user, passwordEncoder.encode(recoveryPassword.getNewPassword()));
            log.info("password changed for userId: {}", user.getId());
            deleteToken(passwordResetToken);

            return ResponseEntity.ok("Password reset successfully!");
        }else
            log.error("passwords entered do not match");
        throw new PasswordMismatchException();
    }


    private boolean isTokenValid(PasswordResetToken token) {
        return token != null && !token.getExpiryDate().before(new Date());
    }

    private void deleteToken(PasswordResetToken token) {
        tokenRepository.delete(token);
    }

    private String generateRandomToken() {
        SecureRandom random = new SecureRandom();
        int TOKEN_LENGTH = 32;
        byte[] bytes = new byte[TOKEN_LENGTH / 2];
        random.nextBytes(bytes);
        return new BigInteger(1, bytes).toString(16);
    }

    private void createToken(User user, String token, Date expiryDate) {
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(expiryDate);
        tokenRepository.save(passwordResetToken);
        log.info("Token created for forgot password function");
    }
}
