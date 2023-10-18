package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.request.ConnectionRequestDto;
import com.matrix.mediconcallapp.service.ConnectionRequestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/connection")
public class ConnectionController {
    private final ConnectionRequestService connectionRequestService;

    // pasiyent ucun hekime istek gondermek
    @PostMapping("/request")
    public ResponseEntity<Void> sendConnectionRequest(HttpServletRequest request,
                                                      @RequestBody ConnectionRequestDto connectionRequestDto){
        connectionRequestService.sendConnection(request, connectionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //hekimin pasiyentden gelen isteyi qebul etmesi
    @PatchMapping("/accept")
    public ResponseEntity<Void> acceptConnectionRequest(HttpServletRequest request,
                                                        @RequestBody Integer patientId){
        connectionRequestService.acceptConnection(request, patientId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //Hekimin connectionu silmeyi
    @DeleteMapping("/doctor")
    public ResponseEntity<Void> deleteConnectionByDoctor(HttpServletRequest request,
                                                         @RequestBody Integer patientId){
        connectionRequestService.deleteConnectionByDoctor(request, patientId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //pasiyentin connectionu silmeyi
    @DeleteMapping("/patient")
    public ResponseEntity<Void> deleteConnectionByPatient(HttpServletRequest request,
                                                          @RequestBody Integer doctorId){
        connectionRequestService.deleteConnectionByPatient(request, doctorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
