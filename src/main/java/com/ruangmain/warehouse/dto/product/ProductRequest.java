package com.ruangmain.warehouse.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @Size(min = 3)
    @Schema(name = "name", example = "jacket")
    private String name;

    @Min(1)
    @Schema(name = "qty", example = "10")
    private int qty;

    @Size(min = 3)
    @Schema(name = "type", example = "garment")
    private String type;
}
