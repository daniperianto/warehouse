package com.ruangmain.warehouse.repository;

import com.ruangmain.warehouse.Util;
import com.ruangmain.warehouse.model.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductRepositoryIntegrationTest {

    @Autowired
    private ProductRepository repository;

    @Test
    public void productRepositorySaveReturnSaveProduct() {
        Product jacket = Util.jacket();

        Product savedJacket = repository.save(jacket);

        Assertions.assertThat(savedJacket).isNotNull();
    }

    @Test
    public void productRepositoryFindAllReturnMoreThanOneProduct() {
        Product jacket = Util.jacket();
        Product motor = Util.motor();

        repository.save(jacket);
        repository.save(motor);
        List<Product> products = repository.findAll();

        Assertions.assertThat(products).isNotNull();
        Assertions.assertThat(products.size()).isEqualTo(2);
    }

    @Test
    public void productRepositoryFindByIdReturnProduct() {
        Product jacket = Util.jacket();

        repository.save(jacket);
        Product savedJacket = repository.findById(jacket.getId()).get();

        Assertions.assertThat(savedJacket).isNotNull();
    }

    @Test
    public void productRepositoryFindByNameReturnProduct() {
        Product jacket = Util.jacket();

        repository.save(jacket);
        Product savedJacket = repository.findByName(jacket.getName()).get();

        Assertions.assertThat(savedJacket).isNotNull();
    }

    @Test
    public void productRepositoryDeleteByIdDeleteProduct() {
        Product jacket = Util.jacket();
        Product motor = Util.motor();

        repository.save(jacket);
        repository.save(motor);
        List<Product> productsBeforeDelete = repository.findAll();
        repository.deleteById(jacket.getId());
        List<Product> productsAfterDelete = repository.findAll();

        Assertions.assertThat(productsBeforeDelete.size()).isEqualTo(2);
        Assertions.assertThat(productsAfterDelete.size()).isEqualTo(1);
    }

}
