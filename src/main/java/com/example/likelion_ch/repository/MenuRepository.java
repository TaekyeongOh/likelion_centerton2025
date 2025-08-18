package com.example.likelion_ch.repository;

import com.example.likelion_ch.entity.Menu;
import com.example.likelion_ch.entity.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByUser(SiteUser user);
    
    Optional<Menu> findByIdAndUserId(Long menuId, Long userId);
    
    @Query("SELECT m FROM Menu m WHERE m.user.id = :userId " +
           "AND (:query IS NULL OR m.nameKo LIKE %:query% OR m.description LIKE %:query%) " +
           "AND (:minPrice IS NULL OR m.price >= :minPrice) " +
           "AND (:maxPrice IS NULL OR m.price <= :maxPrice)")
    Page<Menu> findByUserIdAndSearchCriteria(
            @Param("userId") Long userId,
            @Param("query") String query,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            Pageable pageable);

    Optional<Menu> findByUserAndUserMenuId(SiteUser user, Integer userMenuId);

    @Query("SELECT COALESCE(MAX(m.userMenuId), 0) FROM Menu m WHERE m.user = :user")
    Integer findMaxUserMenuId(@Param("user") SiteUser user);
}
