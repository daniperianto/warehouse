package com.ruangmain.warehouse.repository;

import com.ruangmain.warehouse.model.Product;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends ListCrudRepository<Product, Long> {
    Optional<Product> findByName(String name);

}
