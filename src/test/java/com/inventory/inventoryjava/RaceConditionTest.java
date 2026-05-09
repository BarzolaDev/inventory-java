package com.inventory.inventoryjava;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

public class RaceConditionTest {

    /**
     * MISIL: Demuestra la race condition sin lock.
     * Stock inicial: 10, Threads: 11, cada uno resta 1.
     * Sin protección el stock no queda en -1 sino en un valor inconsistente.
     * Resultado esperado: stock < 0 (falla = race condition demostrada)
     */

    @Test
    void shouldCorruptStockWithoutLock() throws InterruptedException {
        // Setup H2 directo sin Spring
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        ds.setUsername("sa");
        ds.setPassword("");

        JdbcTemplate jdbc = new JdbcTemplate(ds);
        jdbc.execute("CREATE TABLE IF NOT EXISTS stock (id INT, stock_value INT)");
        jdbc.execute("INSERT INTO stock VALUES (1, 10)");

        int threads = 11;
        CountDownLatch latch = new CountDownLatch(threads);
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                try {
                    int current = jdbc.queryForObject("SELECT stock_value FROM stock WHERE id = 1", Integer.class);
                    Thread.sleep(100);
                    jdbc.update("UPDATE stock SET stock_value = ? WHERE id = 1", current - 1);
                } catch (Exception e) {
                    // race condition
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        Integer result = jdbc.queryForObject("SELECT stock_value FROM stock WHERE id = 1", Integer.class);
        System.out.println("Stock final: " + result);
        assertThat(result).isLessThan(0);
    }
}