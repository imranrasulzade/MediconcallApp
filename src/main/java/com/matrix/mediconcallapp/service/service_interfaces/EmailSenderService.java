package com.matrix.mediconcallapp.service.service_interfaces;

import com.matrix.mediconcallapp.model.dto.request.Email;

import org.springframework.stereotype.Service;

@Service
public interface EmailSenderService {
    void sendSimpleEmail(Email email);


}


