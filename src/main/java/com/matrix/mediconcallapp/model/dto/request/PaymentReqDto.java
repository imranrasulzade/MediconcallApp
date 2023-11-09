package com.matrix.mediconcallapp.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class PaymentReqDto {

    @Schema(hidden = true)
    private Integer patientId;

    @NotNull(message = "doctor id can not be null")
    @Min(value = 0)
    @Max(value = 3000)
    private Integer doctorId;

    @NotNull(message = "doctor id can not be null")
    @Min(value = 0)
    @Max(value = 500)
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
