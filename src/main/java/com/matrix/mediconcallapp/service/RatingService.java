package com.matrix.mediconcallapp.service;

import com.matrix.mediconcallapp.model.dto.request.RatingReqDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface RatingService {
    void addRating(HttpServletRequest request, RatingReqDto ratingReqDto);
}
