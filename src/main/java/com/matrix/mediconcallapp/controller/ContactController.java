package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.request.ConnectionRequestDto;
import com.matrix.mediconcallapp.service.ConnectionRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact")
public class ContactController {
    private final ConnectionRequestService connectionRequestService;

    @PostMapping
    public ResponseEntity<Boolean> sendConnectionRequest(ConnectionRequestDto connectionRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(connectionRequestService.sendConnectionRequest(connectionRequestDto));
    }

}
