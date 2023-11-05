package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.enums.ReservationStatus;
import com.matrix.mediconcallapp.model.dto.request.ReservationRequestDto;
import com.matrix.mediconcallapp.model.dto.request.ReservationStatusDto;
import com.matrix.mediconcallapp.model.dto.response.ReservationDto;
import com.matrix.mediconcallapp.model.dto.response.TimeDto;
import com.matrix.mediconcallapp.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
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


    //Hekimin oz rezervasiyalarina baxmasi
    @GetMapping("/doctor")
    public ResponseEntity<List<ReservationDto>> getReservationsOfDoctor(HttpServletRequest request){
        return ResponseEntity.ok(reservationService.getReservationsOfDoctor(request));
    }

    //hekimin qebul etdiyi pasiyentin hekimin butun vaxtlarina baxmasi
    @GetMapping("times/{doctorId}")
    public ResponseEntity<List<TimeDto>> getAllTimes(HttpServletRequest request,
                                     @PathVariable Integer doctorId){
        return ResponseEntity.ok(reservationService.getAllTimes(request, doctorId));
    }

    //Pasiyentin oz reservasiyalarina baxmasi
    @GetMapping("/patient")
    public ResponseEntity<List<ReservationDto>> getByPatient(HttpServletRequest request){
        return ResponseEntity.ok(reservationService.getByPatient(request));
    }


    //pasiyentin reservasiya goturmeyi
    @PostMapping("/request")
    public ResponseEntity<Void> add(HttpServletRequest request,
                                    @RequestBody ReservationRequestDto requestDto){
        reservationService.add(request, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //hekimin pending statusda olan reservasiyalari cekmeyi
    @GetMapping("/view-request")
    public ResponseEntity<List<ReservationDto>> getByStatus(HttpServletRequest request,
                                            @RequestParam ReservationStatus status){
        return ResponseEntity.ok(reservationService.getByStatus(request, status));
    }

    //hekimin reservasiyanin status deyismesi
    @PatchMapping("/status")
    public ResponseEntity<Void> changeStatus(HttpServletRequest request,
                                       @RequestBody ReservationStatusDto reservationStatusDto){
        reservationService.changeStatus(request, reservationStatusDto);
        return ResponseEntity.ok().build();
    }

    //pasiyentin rezervasiyani legv etmesi pasiyent terefden
    @PatchMapping("/cancel")
    public ResponseEntity<Void> cancel(HttpServletRequest request,
                             @RequestBody Integer id){
        reservationService.cancel(request, id);
        return ResponseEntity.ok().build();
    }


}
