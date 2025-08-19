package com.example.likelion_ch.dto;

import com.example.likelion_ch.entity.RestaurantInfo;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuResponse {
    private Long id;
    private Integer userMenuId;
    private String nameKo;
    private String description;
    private BigDecimal price;
    private String imageUrl;  // S3에 업로드된 이미지의 접근 가능한 URL
    private Long version;
    private Instant createdAt;
    private Instant updatedAt;
    private Long userId;

    private RestaurantInfo restaurantInfo;
    private List<MenuInfo> menuList;

}
