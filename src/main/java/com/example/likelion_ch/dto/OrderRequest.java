package com.example.likelion_ch.dto;

import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private Long tableId;
    private Long userId;
    private List<OrderItemRequest> orderItems;
}