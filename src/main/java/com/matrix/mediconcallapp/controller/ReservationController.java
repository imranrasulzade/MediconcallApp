package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.enums.ReservationStatus;
import com.matrix.mediconcallapp.model.dto.request.ReservationRequestDto;
import com.matrix.mediconcallapp.model.dto.request.ReservationStatusDto;
import com.matrix.mediconcallapp.model.dto.response.ReservationDto;
import com.matrix.mediconcallapp.model.dto.response.TimeDto;
import com.matrix.mediconcallapp.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;


    @GetMapping("/doctor")
    public ResponseEntity<List<ReservationDto>> getReservationsOfDoctor(HttpServletRequest request){
        return ResponseEntity.ok(reservationService.getReservationsOfDoctor(request));
    }


    @GetMapping("times/{doctorId}")
    public ResponseEntity<List<TimeDto>> getAllTimes(HttpServletRequest request,
                                     @PathVariable @Pattern(regexp = "^[0-9]+$") Integer doctorId){
        return ResponseEntity.ok(reservationService.getAllTimes(request, doctorId));
    }


    @GetMapping("/patient")
    public ResponseEntity<List<ReservationDto>> getByPatient(HttpServletRequest request){
        return ResponseEntity.ok(reservationService.getByPatient(request));
    }


    @PostMapping("/request")
    public ResponseEntity<Void> add(HttpServletRequest request,
                                    @RequestBody @Valid ReservationRequestDto requestDto){
        reservationService.add(request, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/view-request")
    public ResponseEntity<List<ReservationDto>> getByStatus(HttpServletRequest request,
                                            @RequestParam ReservationStatus status){
        return ResponseEntity.ok(reservationService.getByStatus(request, status));
    }


    @PatchMapping("/status")
    public ResponseEntity<Void> changeStatus(HttpServletRequest request,
                                       @RequestBody @Valid ReservationStatusDto reservationStatusDto){
        reservationService.changeStatus(request, reservationStatusDto);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/cancel")
    public ResponseEntity<Void> cancel(HttpServletRequest request,
                             @RequestBody @NotBlank @Pattern(regexp = "^[0-9]+$") Integer id){
        reservationService.cancel(request, id);
        return ResponseEntity.ok().build();
    }


}
