package com.example.likelion_ch.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {
    private Long id;
    private Integer userMenuId;
    private String nameKo;
    private String description;
    private BigDecimal price;
    private Long version;
    private Instant createdAt;
    private Instant updatedAt;
    private Long userId;
}
