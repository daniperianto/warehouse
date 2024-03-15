package com.ruangmain.warehouse.repository;

import com.ruangmain.warehouse.Util;
import com.ruangmain.warehouse.model.Product;
import com.ruangmain.warehouse.model.ProductHistory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductHistoryRepositoryIntegrationTest {

    @Autowired
    private ProductHistoryRepository historyRepository;

    @Autowired
    private ProductRepository productRepository;


    @Test
    public void productHistoryRepositorySaveReturnSaveProductHistory() {
        Product jacket = Util.jacket();
        Product savedJacket = productRepository.save(jacket);
        ProductHistory sellJacket = Util.sellJacket(savedJacket);

        ProductHistory savedSellJacket = historyRepository.save(sellJacket);

        Assertions.assertThat(savedSellJacket).isNotNull();
    }

    @Test
    public void productHistoryRepositoryFindAllReturnMoreThanOneProductHistory() {
        Product jacket = Util.jacket();
        Product motor = Util.motor();
        Product savedJacket = productRepository.save(jacket);
        Product savedMotor = productRepository.save(motor);
        ProductHistory sellJacket = Util.sellJacket(savedJacket);
        ProductHistory borrowMotor = Util.borrowMotor(savedMotor);


        historyRepository.save(sellJacket);
        historyRepository.save(borrowMotor);
        List<ProductHistory> histories = historyRepository.findAll();

        Assertions.assertThat(histories).isNotNull();
        Assertions.assertThat(histories.size()).isEqualTo(2);
    }
}
