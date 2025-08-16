package com.example.likelion_ch.repository;

import com.example.likelion_ch.entity.StoreFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreFeatureRepository extends JpaRepository<StoreFeature, Long> {
    Optional<StoreFeature> findByName(String name);
}