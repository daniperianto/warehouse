package com.ruangmain.warehouse.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnRequest {
    @NotEmpty
    @Schema(name = "name", example = "jacket")
    private String name;

    @Min(1)
    @Schema(name = "qty", example = "10")
    private int qty;

    @NotEmpty
    @Schema(name = "description", example = "return")
    private String description;
}
