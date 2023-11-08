package com.matrix.mediconcallapp.model.dto.request;

import com.matrix.mediconcallapp.customValidation.ValidUserStatus;
import com.matrix.mediconcallapp.enums.UserStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserStatusDto {
    @NotNull(message = "user id can not be null")
    private Integer id;

    @ValidUserStatus(message = "user status can be ACTIVE, INACTIVE or DELETED")
    private UserStatus userStatus;
}
