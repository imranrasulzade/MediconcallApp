package com.matrix.mediconcallapp.service;

import com.matrix.mediconcallapp.model.dto.request.RatingReqDto;
import com.matrix.mediconcallapp.model.dto.response.RatingRespDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {
    void addRating(HttpServletRequest request, RatingReqDto ratingReqDto);

    List<RatingRespDto> getRating(HttpServletRequest request);
    List<RatingRespDto> getRatingByDoctorId(Integer id);
}
