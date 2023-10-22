package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.request.ContactDto;
import com.matrix.mediconcallapp.model.dto.response.ContactResponseDto;
import com.matrix.mediconcallapp.service.ContactService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact")
public class ContactController {
    private final ContactService contactService;


    //pasiyent uchun
    @GetMapping("/patient-view-contacts")
    public ResponseEntity<List<ContactResponseDto>> getAllForPatient(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(contactService.getAllForPatient(request));
    }

    //hekim uchun
    @GetMapping("/doctor-view-contacts")
    public List<ContactResponseDto> getAllForDoctor(HttpServletRequest request){
        return contactService.getAllForDoctor(request);
    }


    // pasiyent ucun hekime istek gondermek
    @PostMapping("/request")
    public ResponseEntity<Void> connect(HttpServletRequest request,
                                                      @RequestBody ContactDto contactDto){
        contactService.send(request, contactDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //hekimin pasiyentden gelen isteyi qebul etmesi
    @PatchMapping("/accept")
    public ResponseEntity<Void> accept(HttpServletRequest request,
                                                        @RequestBody Integer patientId){
        contactService.accept(request, patientId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //Hekimin connectionu silmeyi
    @DeleteMapping("/doctor")
    public ResponseEntity<Void> deleteByDoctor(HttpServletRequest request,
                                                         @RequestBody Integer patientId){
        contactService.deleteByDoctor(request, patientId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //pasiyentin connectionu silmeyi
    @DeleteMapping("/patient")
    public ResponseEntity<Void> deleteByPatient(HttpServletRequest request,
                                                          @RequestBody Integer doctorId){
        contactService.deleteByPatient(request, doctorId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
