package com.matrix.mediconcallapp.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordDto {
    @Size(min = 8)
    @Size(max = 30)
    @NotNull(message = "current password can not be null")
    String currentPassword;

    @NotNull(message = "new password can not be null")
    @Size(min = 8)
    @Size(max = 30)
    @Pattern(regexp = "[A-Za-z0-9]+")
    String newPassword;

    @NotNull(message = "retry password can not be null")
    @Size(min = 8)
    @Size(max = 30)
    @Pattern(regexp = "[A-Za-z0-9]+")
    String retryPassword;
}
