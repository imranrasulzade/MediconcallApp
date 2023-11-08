package com.matrix.mediconcallapp.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DoctorEditReqDto {

    @Schema(hidden = true)
    private Integer id;

    @Schema(hidden = true)
    private Integer userId;

    @Size(max = 25)
    private String academicTitle;

    @NotNull
    @Size(max = 45)
    private String specialty;

    @Size(max = 45)
    private String placeOfWork;

    @Size(max = 45)
    private String qualification;

    @NotNull
    @Size(min = 16, max = 16)
    @Pattern(regexp = "^[0-9]+$")
    private String bankAccount;

    @Schema(hidden = true)
    private Integer status = 1;
}
