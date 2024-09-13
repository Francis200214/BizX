package com.demo.service;

import com.biz.operation.log.OperationLog;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @OperationLog(category = "ORDER_OPERATION", subcategory = "CREATE_ORDER",
            content = "'Order ID: ' + #order.id + ', User: ' + #order.userId + ', Items: ' + #order.items.size()")
    public void createOrder(Order order) {

    }

    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class Order {
        private String id;
        private String userId;
        private List<Item> items;

        @Setter
        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @ToString
        public static class Item {
            private String name;
            private int quantity;
        }
    }
}

