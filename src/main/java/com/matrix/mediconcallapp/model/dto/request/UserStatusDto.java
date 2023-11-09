package com.matrix.mediconcallapp.model.dto.request;

import com.matrix.mediconcallapp.customValidation.annotation.ValidUserStatus;
import com.matrix.mediconcallapp.enums.UserStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserStatusDto {
    @NotNull(message = "user id can not be null")
    @Min(value = 0)
    @Max(value = 3000)
    private Integer id;

    @ValidUserStatus(message = "user status can be ACTIVE, INACTIVE or DELETED")
    private UserStatus userStatus;
}
