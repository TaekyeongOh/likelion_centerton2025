package com.example.likelion_ch.controller;

import com.example.likelion_ch.dto.OrderRequest;
import com.example.likelion_ch.entity.Order;
import com.example.likelion_ch.service.MenuService;
import com.example.likelion_ch.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);

    private final MenuService menuService;
    // 주문생성
    // 손님용
    @PostMapping("/api/store/orders")
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderRequest request) {
        menuService.createOrder(request);
        return ResponseEntity.status(201).build();

    }

    @GetMapping
    public List<Order> getOrders() {
        return orderService.getAllOrders();
    }
}
