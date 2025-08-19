package com.example.likelion_ch.service;

import com.example.likelion_ch.dto.OrderRequest;
import com.example.likelion_ch.dto.OrderItemRequest;
import com.example.likelion_ch.dto.OrderItemOptionRequest;
import com.example.likelion_ch.entity.*;
import com.example.likelion_ch.repository.MenuRepository;
import com.example.likelion_ch.repository.OrderRepository;
import com.example.likelion_ch.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final SiteUserRepository userRepository;

    @Transactional
    public Order createOrder(OrderRequest request) {
        // 사용자(가게) 가져오기
        SiteUser user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        // 주문 생성
        Order order = Order.builder()
                .user(user)
                .tableId(request.getTableId())
                .build();

        // 주문 아이템 생성
        for (OrderItemRequest itemRequest : request.getOrderItems()) {
            Menu menu = menuRepository.findById(itemRequest.getMenuId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다."));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .menu(menu)
                    .quantity(itemRequest.getQuantity())
                    .build();

            // 옵션 처리
            if (itemRequest.getOptions() != null) {
                for (OrderItemOptionRequest optionRequest : itemRequest.getOptions()) {
                    orderItem.addOption(optionRequest.toEntity(orderItem));
                }
            }

            order.addOrderItem(orderItem);
        }

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
