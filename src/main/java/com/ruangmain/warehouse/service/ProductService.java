package com.ruangmain.warehouse.service;

import com.ruangmain.warehouse.dto.product.ProductRequest;
import com.ruangmain.warehouse.model.Product;
import com.ruangmain.warehouse.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    public Product add(ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .qty(request.getQty())
                .type(request.getType())
                .build();
        return repository.save(product);
    }

    public Product addProductQuantity(Product product, int qty) {
        product.setQty(product.getQty() + qty);
        return repository.save(product);
    }

    public String delete(Long id) {
        repository.deleteById(id);
        return "success deleting product";
    }

    public void reduceProductQty(Product product, int qty) {
        product.setQty(product.getQty() - qty);
        repository.save(product);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Optional<Product> findByName(String name) {
        return repository.findByName(name);
    }
}
