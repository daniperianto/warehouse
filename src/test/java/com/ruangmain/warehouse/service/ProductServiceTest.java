package com.ruangmain.warehouse.service;


import com.ruangmain.warehouse.Util;
import com.ruangmain.warehouse.dto.product.ProductRequest;
import com.ruangmain.warehouse.model.Product;
import com.ruangmain.warehouse.model.User;
import com.ruangmain.warehouse.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    public void productServiceFindByIdReturnOptionalProduct() {
        Optional<Product> jacket = Optional.of(Util.jacket());

        when(repository.findById(Mockito.anyLong())).thenReturn(jacket);
        Optional<Product> jacketResult = service.findById(Mockito.anyLong());

        Assertions.assertThat(jacketResult).isEqualTo(jacket);
    }

    @Test void productServiceAddReturnSavedProduct() {
        ProductRequest jacketRequest = Util.productRequestJacket();
        Product jacket = Util.jacket();

        when(repository.save(Mockito.any(Product.class))).thenReturn(jacket);
        Product jacketResult = service.add(jacketRequest);

        Assertions.assertThat(jacketResult).isEqualTo(jacket);
    }

    @Test void productServiceAddQtyIncreaseQty() {
        Product jacket = Util.jacket();

        when(repository.save(Mockito.any(Product.class))).thenReturn(jacket);
        Product jacketResult = service.addProductQuantity(jacket, Mockito.anyInt());

        Assertions.assertThat(jacketResult).isEqualTo(jacket);
    }
}
