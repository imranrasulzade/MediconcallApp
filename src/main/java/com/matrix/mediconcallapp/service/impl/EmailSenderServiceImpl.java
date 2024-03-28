package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.model.dto.request.Email;
import com.matrix.mediconcallapp.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:application.properties")
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender javaMailSender;

    @Value("${application.my-email}")
    private static String EMAIL;
    @Override
    public void sendSimpleEmail(Email email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email.getReceiver());
        msg.setFrom(EMAIL);
        msg.setSubject(email.getSubject());
        msg.setText(email.getText());
        javaMailSender.send(msg);
        log.info("Successfully sent email to {}", email.getReceiver());
    }
}
