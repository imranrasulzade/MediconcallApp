package com.matrix.mediconcallapp.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class PaymentReqDto {

    @Schema(hidden = true)
    private Integer patientId;

    @NotBlank(message = "doctor id can not be empty or null")
    @Pattern(regexp = "^[0-9]+$")
    private Integer doctorId;

    @NotBlank(message = "doctor id can not be empty or null")
    @Pattern(regexp = "^[0-9]+$")
    private Integer amount;

    @Schema(hidden = true)
    private String senderCard;

    @Schema(hidden = true)
    private String receiverCard;

    @Schema(hidden = true)
    @CreationTimestamp
    private LocalDateTime timestamp;

    @Schema(hidden = true)
    private Integer status = 1;
}
