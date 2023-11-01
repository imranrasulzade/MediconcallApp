package com.matrix.mediconcallapp.service;

import com.matrix.mediconcallapp.model.dto.request.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.io.File;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    private final String myEmail = "rasulzade002@gmail.com";


    public void sendSimpleEmail(Email email) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email.getReceiver());
        msg.setFrom(myEmail);
        msg.setSubject(email.getSubject());
        msg.setText(email.getText());
        javaMailSender.send(msg);
        log.info("Successfully sent email to {}", email.getReceiver());
    }

    public void sendEmailWithAttachment(Email email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(myEmail);
        helper.setTo(email.getReceiver());
        helper.setSubject(email.getSubject());
        helper.setText(email.getText());
        FileSystemResource file = new FileSystemResource(new File(email.getAttachmentPath()));
        helper.addAttachment(email.getFileName(), file);
        javaMailSender.send(message);
        log.info("Successfully sent email to {}", email.getReceiver());
    }

    public void sendEmailWithTemplate(Email email) throws MessagingException {
        Context context = new Context();
        context.setVariable("message", email.getText());

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        helper.setTo(email.getReceiver());
        helper.setFrom(myEmail);
        helper.setSubject(email.getSubject());

        String htmlContent = templateEngine.process("email-template-for-test", context);
        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);

    }


}


