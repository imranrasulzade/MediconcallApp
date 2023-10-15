package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.response.ReservationDto;
import com.matrix.mediconcallapp.model.dto.response.TimeDto;
import com.matrix.mediconcallapp.repository.ReservationRepository;
import com.matrix.mediconcallapp.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationRepository repository;

    @GetMapping
    public List<ReservationDto> getReservations(){
        return reservationService.getAllReservations();
    }

    //Hekimin rezervasiyalarina baxmaq
    @GetMapping("/doctor/{id}")
    public List<ReservationDto> getReservationsOfDoctor(@PathVariable Integer id){
        return reservationService.getReservationsOfDoctor(id);
    }

    //hekimin qebul etdiyi pasiyentin hekimin butun vaxtlarina baxmagi
    @GetMapping("{patientId}/{doctorId}")
    public List<TimeDto> getAllTimes(@PathVariable Integer patientId, Integer doctorId){
        return reservationService.getAllTimes(patientId, doctorId);
    }

    //Pasiyentin reservasiyalarina baxmaq
    @GetMapping("/{patientId}")
    public List<ReservationDto> getReservationsOfPatient(@PathVariable Integer patientId){
        return reservationService.getReservationsOfPatient(patientId);
    }


    //YOXLAMAQ UCHUN OLAN CONTROLLER
//    @GetMapping("/test/{pid}/{did}")
//    public List<Reservation> test(@PathVariable Integer pid, Integer did){
//        return repository.findHello(pid, did);
//    }
}
