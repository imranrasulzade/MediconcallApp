package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.enums.UserStatus;
import com.matrix.mediconcallapp.model.dto.request.UserStatusDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorDto;
import com.matrix.mediconcallapp.model.dto.response.ReservationDto;
import com.matrix.mediconcallapp.model.dto.response.UserDto;
import com.matrix.mediconcallapp.service.DoctorService;
import com.matrix.mediconcallapp.service.ReservationService;
import com.matrix.mediconcallapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final ReservationService reservationService;
    private final UserService userService;
    private final DoctorService doctorService;

    //id-ye gore hekimin datalari
    @GetMapping("/doctor/{id}")
    public DoctorDto getDoctorById(@PathVariable Integer id){
        return doctorService.getById(id);
    }

    //butun hekimlerin siyahisi
    @GetMapping("doctors")
    public List<DoctorDto> getAll(){
        return doctorService.getAll();
    }

    @GetMapping("reservations")
    public ResponseEntity<List<ReservationDto>> getReservations(){
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("view-status")
    public ResponseEntity<List<UserDto>> getUserByStatus(@RequestParam UserStatus userStatus){
        return ResponseEntity.ok(userService.getUserByStatus(userStatus));
    }

    @PatchMapping("status")
    public ResponseEntity<Void> updateStatus(@Valid @RequestBody UserStatusDto userStatusDto){
        userService.updateStatus(userStatusDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
