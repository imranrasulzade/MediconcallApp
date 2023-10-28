package com.matrix.mediconcallapp.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RecoveryPassword {
    @NotNull(message = "token can not be null")
    String token;

    @NotNull(message = "new password can not be null")
    @Size(min = 8)
    @Pattern(regexp = "[A-Za-z0-9]+")
    String newPassword;

    @NotNull(message = "retry password can not be null")
    @Size(min = 8)
    @Pattern(regexp = "[A-Za-z0-9]+")
    String retryPassword;
}
