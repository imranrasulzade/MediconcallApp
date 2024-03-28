package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.model.dto.request.Email;
import com.matrix.mediconcallapp.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender javaMailSender;
    @Override
    public void sendSimpleEmail(Email email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email.getReceiver());
        String myEmail = "rasulzade002@gmail.com";
        msg.setFrom(myEmail);
        msg.setSubject(email.getSubject());
        msg.setText(email.getText());
        javaMailSender.send(msg);
        log.info("Successfully sent email to {}", email.getReceiver());
    }
}
