package com.ruangmain.warehouse.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotEmpty
    @Schema(name = "username", example = "user")
    private String username;

    @Email
    @NotEmpty
    @Schema(name = "email", example = "user@mail.com")
    private String email;

    @Size(min = 8)
    @Schema(name = "password", example = "user-user")
    private String password;

    @Pattern(regexp = "ADMIN|USER")
    @Schema(name = "role", example = "USER")
    private String role;
}
