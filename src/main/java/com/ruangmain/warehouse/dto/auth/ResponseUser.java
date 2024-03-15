package com.ruangmain.warehouse.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUser {
    @Schema(name = "id", example = "2")
    private Long id;
    @Schema(name = "username", example = "user")
    private String username;
    @Schema(name = "email", example = "user@mail.com")
    private String email;
}
