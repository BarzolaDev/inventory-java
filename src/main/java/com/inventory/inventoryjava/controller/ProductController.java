package com.inventory.inventoryjava.controller;

import com.inventory.inventoryjava.dto.StockUpdateRequest;
import com.inventory.inventoryjava.model.Product;
import com.inventory.inventoryjava.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/{id}/stock")
    public ResponseEntity<Product> updateStock(
            @PathVariable Long id,
            @RequestBody StockUpdateRequest request) {
        Product updated = productService.updateStock(id, request.quantity());
        return ResponseEntity.ok(updated);
    }
}