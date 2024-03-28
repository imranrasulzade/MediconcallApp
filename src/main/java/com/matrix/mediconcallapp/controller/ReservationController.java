package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.enums.ReservationStatus;
import com.matrix.mediconcallapp.model.dto.request.ReservationRequestDto;
import com.matrix.mediconcallapp.model.dto.request.ReservationStatusDto;
import com.matrix.mediconcallapp.model.dto.response.ReservationDto;
import com.matrix.mediconcallapp.model.dto.response.TimeDto;
import com.matrix.mediconcallapp.service.service_interfaces.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;


    @GetMapping("/doctor")
    public List<ReservationDto> getReservationsOfDoctor(HttpServletRequest request){
        return reservationService.getReservationsOfDoctor(request);
    }


    @GetMapping("/patient/doctor-times/{doctorId}")
    public List<TimeDto> getAllTimes(HttpServletRequest request,
                                     @PathVariable @Pattern(regexp = "^[0-9]+$") Integer doctorId){
        return reservationService.getAllTimes(request, doctorId);
    }


    @GetMapping("/patient")
    public List<ReservationDto> getByPatient(HttpServletRequest request){
        return reservationService.getByPatient(request);
    }


    @PostMapping("/patient/request")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(HttpServletRequest request,
                                    @RequestBody @Valid ReservationRequestDto requestDto){
        reservationService.add(request, requestDto);
    }


    @GetMapping("/doctor/view-status")
    public List<ReservationDto> getByStatus(HttpServletRequest request,
                                            @RequestParam ReservationStatus status){
        return reservationService.getByStatus(request, status);
    }



    @PatchMapping("/doctor/status")
    public void changeStatus(HttpServletRequest request,
                                       @RequestBody @Valid ReservationStatusDto reservationStatusDto){
        reservationService.changeStatus(request, reservationStatusDto);
    }


    @PatchMapping("/patient/cancel")
    public void cancel(HttpServletRequest request,
                             @RequestBody @NotBlank @Pattern(regexp = "^[0-9]+$") Integer id){
        reservationService.cancel(request, id);
    }


    @GetMapping("/admin/reservations")
    public List<ReservationDto> getReservations(){
        return reservationService.getAllReservations();
    }


}
