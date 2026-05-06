package com.inventory.inventoryjava.service;

import com.inventory.inventoryjava.domain.InsufficientStockException;
import com.inventory.inventoryjava.domain.ProductNotFoundException;
import com.inventory.inventoryjava.model.Product;
import com.inventory.inventoryjava.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(timeout = 3)
    public Product updateStock(Long id, Integer quantity) {
        Product product = productRepository.findByIdWithLock(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));

        if (product.getStock() + quantity < 0) {
            throw new InsufficientStockException("Insufficient stock");
        }

        product.setStock(product.getStock() + quantity);
        return productRepository.save(product);
    }

}
