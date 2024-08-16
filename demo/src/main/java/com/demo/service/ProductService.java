package com.demo.service;

import com.biz.operation.log.OperationLog;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @OperationLog(category = "PRODUCT_OPERATION", subcategory = "ADD_PRODUCT",
            content = "'New product added: ' + #product.name + ' (Category: ' + #product.category + ') with Stock: ' + #product.stock")
    public void addProduct(Product product) {

    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Product {
        private String name;
        private String category;
        private int stock;
    }
}

