package com.ruangmain.warehouse.controller;

import com.ruangmain.warehouse.dto.product.ProductUsageRequest;
import com.ruangmain.warehouse.dto.product.ReturnRequest;
import com.ruangmain.warehouse.model.Action;
import com.ruangmain.warehouse.model.Product;
import com.ruangmain.warehouse.model.ProductHistory;
import com.ruangmain.warehouse.service.ProductHistoryService;
import com.ruangmain.warehouse.service.ProductService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductHistoryController {

    private final ProductHistoryService historyService;
    private final ProductService productService;

    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n" +
            "  \"id\": 2,\n" +
            "  \"product\": {\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"jacket\",\n" +
            "    \"qty\": 190,\n" +
            "    \"type\": \"garment\",\n" +
            "    \"created_at\": \"2024-03-15T03:18:47.627+00:00\",\n" +
            "    \"updated_at\": \"2024-03-15T03:18:47.627+00:00\"\n" +
            "  },\n" +
            "  \"qty\": 10,\n" +
            "  \"action\": \"USING\",\n" +
            "  \"description\": \"selling\",\n" +
            "  \"created_at\": \"2024-03-15T03:25:35.906+00:00\",\n" +
            "  \"updated_at\": \"2024-03-15T03:25:35.906+00:00\"\n" +
            "}"
            )))
    @PostMapping("/taking-product")
    public ResponseEntity<?> takingProduct(@Valid @RequestBody ProductUsageRequest request) {
        // check if product exist or not
        Product product;
        Optional<Product> opsProduct = productService.findByName(request.getName());
        if (opsProduct.isPresent()) {
            product = opsProduct.get();
        } else {
            return ResponseEntity.badRequest().body("product doesn't exist");
        }

        // check if qty product is enough
        if (product.getQty() < request.getQty()) {
            return ResponseEntity.badRequest().body("product qty is not enough");
        }

        // save the change
        productService.reduceProductQty(product, request.getQty());
        ProductHistory productHistory = ProductHistory.builder()
                .product(product)
                .qty(request.getQty())
                .action(Action.valueOf(request.getAction()))
                .description(request.getDescription())
                .build();
        return ResponseEntity.ok(historyService.add(productHistory));
    }

    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n" +
            "  \"id\": 2,\n" +
            "  \"product\": {\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"jacket\",\n" +
            "    \"qty\": 190,\n" +
            "    \"type\": \"garment\",\n" +
            "    \"created_at\": \"2024-03-15T03:18:47.627+00:00\",\n" +
            "    \"updated_at\": \"2024-03-15T03:18:47.627+00:00\"\n" +
            "  },\n" +
            "  \"qty\": 10,\n" +
            "  \"action\": \"RETURN\",\n" +
            "  \"description\": \"return\",\n" +
            "  \"created_at\": \"2024-03-15T03:25:35.906+00:00\",\n" +
            "  \"updated_at\": \"2024-03-15T03:25:35.906+00:00\"\n" +
            "}"
    )))
    @PostMapping("/return")
    public ResponseEntity<?> returnProduct(@Valid @RequestBody ReturnRequest request) {
        // check if product exist or not
        Product product;
        Optional<Product> opsProduct = productService.findByName(request.getName());
        if (opsProduct.isPresent()) {
            product = opsProduct.get();
        } else {
            return ResponseEntity.badRequest().body("product doesn't exist");
        }

        // save the change
        productService.addProductQuantity(product, request.getQty());
        ProductHistory productHistory = ProductHistory.builder()
                .product(product)
                .qty(request.getQty())
                .action(Action.RETURN)
                .description(request.getDescription())
                .build();
        return ResponseEntity.ok(historyService.add(productHistory));
    }

    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n" +
            "  \"id\": 2,\n" +
            "  \"product\": {\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"jacket\",\n" +
            "    \"qty\": 190,\n" +
            "    \"type\": \"garment\",\n" +
            "    \"created_at\": \"2024-03-15T03:18:47.627+00:00\",\n" +
            "    \"updated_at\": \"2024-03-15T03:18:47.627+00:00\"\n" +
            "  },\n" +
            "  \"qty\": 10,\n" +
            "  \"action\": \"USING\",\n" +
            "  \"description\": \"selling\",\n" +
            "  \"created_at\": \"2024-03-15T03:25:35.906+00:00\",\n" +
            "  \"updated_at\": \"2024-03-15T03:25:35.906+00:00\"\n" +
            "}"
    )))
    @GetMapping("/history")
    public ResponseEntity<List<ProductHistory>> getAllProductHistory() {
        return ResponseEntity.ok(historyService.findAll());
    }

}
