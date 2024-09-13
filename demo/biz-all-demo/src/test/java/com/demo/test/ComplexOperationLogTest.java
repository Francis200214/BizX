package com.demo.test;

import com.demo.service.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class ComplexOperationLogTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private FinanceService financeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProjectService projectService;

    @Test
    public void test1() throws InterruptedException {

        // 创建一个线程池，模拟并发场景
        ExecutorService executorService = Executors.newFixedThreadPool(200);

        // 模拟复杂数据输入
        List<OrderService.Order> orders = createOrders();
        List<String> users = createUsers();
        List<FinanceService.Payment> payments = createPayments();
        List<ProductService.Product> products = createProducts();
        List<ProjectService.Project> projects = createProjects();

        // 并发执行各个业务操作
        for (OrderService.Order order : orders) {
            executorService.submit(() -> orderService.createOrder(order));
        }

        for (String user : users) {
            executorService.submit(() -> userService.login(user, "password123", "192.168.1.1"));
        }

        for (FinanceService.Payment payment : payments) {
            executorService.submit(() -> financeService.payBonus(payment.employeeId, payment.amount));
        }

        for (ProductService.Product product : products) {
            executorService.submit(() -> productService.addProduct(product));
        }

        for (ProjectService.Project project : projects) {
            executorService.submit(() -> projectService.createProject(project));
        }

        // 关闭线程池并等待任务完成
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);

        System.out.println("Complex operation log test completed.");
    }

    private static List<OrderService.Order> createOrders() {
        List<OrderService.Order> orders = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            OrderService.Order order = new OrderService.Order();
            order.setId("ORD" + i);
            order.setUserId("USER" + (i % 10));
            List<OrderService.Order.Item> items = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                OrderService.Order.Item item = new OrderService.Order.Item();
                item.setName("Item" + j);
                item.setQuantity(j + 1);
                items.add(item);
            }
            order.setItems(items);
            orders.add(order);
        }
        return orders;
    }

    private static List<String> createUsers() {
        List<String> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            users.add("User" + i);
        }
        return users;
    }

    private static List<FinanceService.Payment> createPayments() {
        List<FinanceService.Payment> payments = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            FinanceService.Payment payment = new FinanceService.Payment();
            payment.employeeId = "EMP" + i;
            payment.amount = 1000.0 + i * 100;
            payments.add(payment);
        }
        return payments;
    }

    private static List<ProductService.Product> createProducts() {
        List<ProductService.Product> products = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            ProductService.Product product = new ProductService.Product();
            product.setName("Product" + i);
            product.setCategory("Category" + (i % 5));
            product.setStock(100 + i * 10);
            products.add(product);
        }
        return products;
    }

    private static List<ProjectService.Project> createProjects() {
        List<ProjectService.Project> projects = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ProjectService.Project project = new ProjectService.Project();
            project.setName("Project" + i);
            project.setStartDate("2024-08-01");
            project.setEndDate("2024-12-31");
            projects.add(project);
        }
        return projects;
    }



}
