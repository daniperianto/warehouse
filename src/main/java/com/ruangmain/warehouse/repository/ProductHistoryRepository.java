package com.ruangmain.warehouse.repository;

import com.ruangmain.warehouse.model.ProductHistory;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductHistoryRepository extends ListCrudRepository<ProductHistory, Long> {
}
