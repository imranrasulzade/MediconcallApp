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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    //butun rezervasiyalar admin uchun
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("view-all")
    public List<ReservationDto> getReservations(){
        return reservationService.getAllReservations();
    }

    //Hekimin oz rezervasiyalarina baxmasi
    @GetMapping("/doctor")
    public List<ReservationDto> getReservationsOfDoctor(HttpServletRequest request){
        return reservationService.getReservationsOfDoctor(request);
    }

    //hekimin qebul etdiyi pasiyentin hekimin butun vaxtlarina baxmasi
    @GetMapping("times/{doctorId}")
    public List<TimeDto> getAllTimes(HttpServletRequest request,
                                     @PathVariable Integer doctorId){
        return reservationService.getAllTimes(request, doctorId);
    }

    //Pasiyentin oz reservasiyalarina baxmasi
    @GetMapping("/patient")
    public List<ReservationDto> getByPatient(HttpServletRequest request){
        return reservationService.getByPatient(request);
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
    public List<ReservationDto> getByStatus(HttpServletRequest request,
                                            @RequestParam ReservationStatus status){
        return reservationService.getByStatus(request, status);
    }

    //hekimin reservasiyanin status deyismesi
    @PatchMapping("/status")
    public void changeStatus(HttpServletRequest request,
                                       @RequestBody ReservationStatusDto reservationStatusDto){
        reservationService.changeStatus(request, reservationStatusDto);
    }

    //pasiyentin rezervasiyani legv etmesi
    @PatchMapping("/cancel")
    public void cancel(HttpServletRequest request,
                             @RequestBody Integer id){
        reservationService.cancel(request, id);
    }


}
