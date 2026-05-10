package com.inventory.inventoryjava;

import com.inventory.inventoryjava.model.Product;
import com.inventory.inventoryjava.repository.ProductRepository;
import com.inventory.inventoryjava.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class ProductConcurrencyTest {

    static {
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
    }

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void selectForUpdatePreventsRaceCondition() throws InterruptedException {
        // Arrange
        Product product = new Product(null, "test", 1, BigDecimal.ONE, BigDecimal.TEN, "kg");
        Product saved = productRepository.save(product);

        int threads = 2;
        CountDownLatch latch = new CountDownLatch(threads);
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        // Act - 10 usuarios simultáneos
        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                try {
                    productService.updateStock(saved.getId(), -1);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        // Assert
        Product result = productRepository.findById(saved.getId()).orElseThrow();
        assertThat(result.getStock()).isEqualTo(-1);
    }
}