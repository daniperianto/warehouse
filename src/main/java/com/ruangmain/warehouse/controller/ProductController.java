package com.ruangmain.warehouse.controller;

import com.ruangmain.warehouse.dto.product.ProductRequest;
import com.ruangmain.warehouse.model.Product;
import com.ruangmain.warehouse.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProduct() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable @Parameter(example = "2") Long id) {
        // check if product exist or not
        Optional<Product> product = service.findById(id);
        if (product.isEmpty()) return ResponseEntity.badRequest().body("product doesn't exist");

        return ResponseEntity.ok(product.get());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest request) {
        // check if product already exist or not
        Optional<Product> product = service.findByName(request.getName());
        if (product.isPresent()) return ResponseEntity.badRequest().body("product already exist");

        return ResponseEntity.ok(service.add(request));
    }

    @PutMapping("/{id}/add-quantity")
    public ResponseEntity<?> addProductQty(@PathVariable @Parameter(example = "2") Long id,
                                           @RequestParam @Parameter(example = "2") int qty) {
        // check if product exist or not
        Optional<Product> product = service.findById(id);
        if (product.isEmpty()) return ResponseEntity.badRequest().body("product doesn't exist");

        return ResponseEntity.ok(service.addProductQuantity(product.get(), qty));
    }


    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable @Parameter(example = "2") Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

}
