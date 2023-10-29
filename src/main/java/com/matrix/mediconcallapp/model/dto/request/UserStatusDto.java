package com.matrix.mediconcallapp.model.dto.request;

import com.matrix.mediconcallapp.Annotation.ValidUserStatus;
import com.matrix.mediconcallapp.enums.UserStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserStatusDto {
    @NotNull(message = "user id can not be null")
    Integer id;

    @ValidUserStatus
    UserStatus userStatus;
}
