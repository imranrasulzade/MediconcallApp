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

    @NotBlank
    @Pattern(regexp = "^[0-9]+$")
    private Integer doctorId;

    @Min(0)
    @Max(5)
    @NotBlank(message = "rating value can not b empty or null!")
    @Pattern(regexp = "^[0-5]+$")
    private Integer ratingValue;

    private String comment;

    @Schema(hidden = true)
    private LocalDateTime timestamp;

    @Schema(hidden = true)
    private Integer status = 1;
}
