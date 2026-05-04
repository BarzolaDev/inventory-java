package com.inventory.inventoryjava;

import org.springframework.boot.SpringApplication;

public class TestInventoryJavaApplication {

    public static void main(String[] args) {
        SpringApplication.from(InventoryJavaApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
