package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.Reservation;
import com.matrix.mediconcallapp.model.dto.response.ReservationDto;
import com.matrix.mediconcallapp.model.dto.response.TimeDto;
import com.matrix.mediconcallapp.repository.ReservationRepository;
import com.matrix.mediconcallapp.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/reservations")
    public List<ReservationDto> getReservations(){
        return reservationService.getAllReservations();
    }

    //Hekimin rezervasiyalarina baxmaq
    @GetMapping("/doctor")
    public List<ReservationDto> getReservationsOfDoctor(HttpServletRequest request){
        return reservationService.getReservationsOfDoctor(request);
    }

    //hekimin qebul etdiyi pasiyentin hekimin butun vaxtlarina baxmagi
    @GetMapping("view/{doctorId}")
    public List<TimeDto> getAllTimes(HttpServletRequest request,
                                     @PathVariable Integer doctorId){
        return reservationService.getAllTimes(request, doctorId);
    }

    //Pasiyentin reservasiyalarina baxmaq
    @GetMapping("/patient")
    public List<ReservationDto> getReservationsOfPatient(HttpServletRequest request){
        return reservationService.getReservationsOfPatient(request);
    }



}
