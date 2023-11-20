package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.request.RatingReqDto;
import com.matrix.mediconcallapp.model.dto.response.RatingRespDto;
import com.matrix.mediconcallapp.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rating")
public class RatingController {

    private final RatingService ratingService;


    @PostMapping("patient/doctor/rate")
    @ResponseStatus(HttpStatus.CREATED)
    public void addRating(HttpServletRequest request,
                                          @RequestBody @Valid RatingReqDto reqDto){
        ratingService.addRating(request, reqDto);
    }


    @GetMapping("patient/doctor-comments/{id}")
    public List<RatingRespDto> getRatingByDoctorId(@PathVariable @Pattern(regexp = "^[0-9]+$") Integer id){
        return ratingService.getRatingByDoctorId(id);
    }


    @GetMapping("doctor")
    public List<RatingRespDto> getRating(HttpServletRequest request){
        return ratingService.getRating(request);
    }
}
