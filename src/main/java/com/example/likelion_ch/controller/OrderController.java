package com.example.likelion_ch.controller;

import com.example.likelion_ch.dto.CreateOrderRequest;
import com.example.likelion_ch.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class OrderController {

    private final MenuService menuService;

    // 주문 생성 (손님용)
    @PostMapping("/api/store/orders")
    public ResponseEntity<Void> createOrder(@RequestBody CreateOrderRequest request) {
        menuService.createOrder(request);
        return ResponseEntity.status(201).build();
    }
}