package com.matrix.mediconcallapp.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorEditReqDto {

    @Schema(hidden = true)
    private Integer id;

    @Schema(hidden = true)
    private Integer userId;


    private String academicTitle;

    @NotNull
    private String specialty;
    private String placeOfWork;
    private String qualification;

    @NotNull
    private String bankAccount;

    @Schema(hidden = true)
    private Integer status = 1;
}
