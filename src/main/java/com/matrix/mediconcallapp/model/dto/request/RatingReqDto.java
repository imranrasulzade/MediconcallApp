package com.matrix.mediconcallapp.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RatingReqDto {
    @Schema(hidden = true)
    private Integer id;

    @Schema(hidden = true)
    private Integer patientId;

    @NotNull(message = "doctor id can not be null")
    private Integer doctorId;

    @Min(value = 0)
    @Max(value = 5)
    @NotNull(message = "rating value can not be null!")
    private Integer ratingValue;

    private String comment;

    @Schema(hidden = true)
    private LocalDateTime timestamp;

    @Schema(hidden = true)
    private Integer status = 1;
}
