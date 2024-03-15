package com.ruangmain.warehouse.service;

import com.ruangmain.warehouse.model.ProductHistory;
import com.ruangmain.warehouse.repository.ProductHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductHistoryService {

    private final ProductHistoryRepository repository;

    public ProductHistory add(ProductHistory productHistory) {
        return repository.save(productHistory);
    }

    public List<ProductHistory> findAll() {
        return repository.findAll();
    }
}
