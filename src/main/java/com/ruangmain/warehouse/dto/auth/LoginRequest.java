package com.ruangmain.warehouse.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Email
    @NotEmpty
    @Schema(name = "email", example = "user@mail.com")
    private String email;

    @Size(min = 8)
    @Schema(name = "password", example = "user-user")
    private String password;
}
