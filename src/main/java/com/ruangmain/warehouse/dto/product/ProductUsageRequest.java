package com.ruangmain.warehouse.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUsageRequest {
    @NotEmpty
    @Schema(name = "name", example = "jacket")
    private String name;

    @Min(1)
    @Schema(name = "qty", example = "10")
    private int qty;

    @NotEmpty
    @Schema(name = "description", example = "selling")
    private String description;

    @Pattern(regexp = "BORROW|USING")
    @Schema(name = "action", example = "USING")
    private String action;
}
