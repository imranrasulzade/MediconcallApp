package com.matrix.mediconcallapp.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RatingReqDto {
    @Schema(hidden = true)
    private Integer id;

    @Schema(hidden = true)
    private Integer patientId;
    private Integer doctorId;

    @Min(0)
    @Max(5)
    @NotNull(message = "rating value can not be null!")
    private Integer ratingValue;

    private String comment;

    @Schema(hidden = true)
    private LocalDateTime timestamp;

    @Schema(hidden = true)
    private Integer status = 1;
}
