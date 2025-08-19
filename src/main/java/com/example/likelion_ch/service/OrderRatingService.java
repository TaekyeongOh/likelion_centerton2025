package com.example.likelion_ch.service;

import com.example.likelion_ch.entity.Order;
import com.example.likelion_ch.entity.OrderRating;
import com.example.likelion_ch.repository.OrderRatingRepository;
import com.example.likelion_ch.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderRatingService {

    private final OrderRatingRepository ratingRepository;
    private final OrderRepository orderRepository;

    /**
     * 손님이 별점 저장
     */
    @Transactional
    public OrderRating saveRating(Long orderId, int star) {
        // 트랜잭션 안에서 Order와 User Lazy 로딩 가능
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        OrderRating rating = OrderRating.builder()
                .star(star)
                .order(order)
                .createdAt(LocalDateTime.now())
                .build();

        return ratingRepository.save(rating);
    }

    /**
     * 사장님: 최근 N개의 별점 조회
     * Lazy 로딩 User 정보 포함
     */
    @Transactional(readOnly = true)
    public List<OrderRating> getRecentRatings(int limit) {
        List<OrderRating> ratings = ratingRepository.findAllByOrderByCreatedAtDesc(
                PageRequest.of(0, limit, Sort.by("createdAt").descending())
        );

        // Lazy 로딩된 User 정보 초기화
        ratings.forEach(r -> {
            if (r.getOrder() != null && r.getOrder().getUser() != null) {
                r.getOrder().getUser().getId(); // User 엔티티 강제 초기화
            }
        });

        return ratings;
    }
}
