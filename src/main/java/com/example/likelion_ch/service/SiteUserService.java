package com.example.likelion_ch.service;

import com.example.likelion_ch.dto.UserLoginRequest;
import com.example.likelion_ch.dto.UserLoginResponse;
import com.example.likelion_ch.dto.UserRegisterStep1Request;
import com.example.likelion_ch.dto.UserRegisterStep2Request;
import com.example.likelion_ch.entity.SiteUser;
import com.example.likelion_ch.entity.StoreFeature;
import com.example.likelion_ch.repository.SiteUserRepository;
import com.example.likelion_ch.repository.StoreFeatureRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteUserService {
    private final SiteUserRepository siteUserRepository;
    private final StoreFeatureRepository storeFeatureRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public SiteUserService(SiteUserRepository siteUserRepository,
                           StoreFeatureRepository storeFeatureRepository) {
        this.siteUserRepository = siteUserRepository;
        this.storeFeatureRepository = storeFeatureRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // 1단계 회원가입: SiteUser 엔티티를 반환하도록 수정
    @Transactional
    public SiteUser registerStep1(UserRegisterStep1Request request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        if (siteUserRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        SiteUser user = new SiteUser();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return siteUserRepository.save(user);
    }

    // 2단계 가게 정보 등록: SiteUser 엔티티를 반환하도록 수정
    @Transactional
    public SiteUser registerStep2(Long userId, UserRegisterStep2Request request) {
        SiteUser user = siteUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        user.setRestaurantName(request.getRestaurantName());
        user.setRestaurantAddress(request.getRestaurantAddress());
        user.setDescription(request.getDescription());
        user.setTableCount(request.getTableCount());

        List<StoreFeature> features = request.getFeatures().stream()
                .map(name -> storeFeatureRepository.findByName(name)
                        .orElseGet(() -> {
                            StoreFeature newFeature = new StoreFeature();
                            newFeature.setName(name);
                            return storeFeatureRepository.save(newFeature);
                        })
                ).collect(Collectors.toList());
        user.setFeatures(features);

        return siteUserRepository.save(user);
    }

    // 로그인
    @Transactional(readOnly = true)
    public UserLoginResponse login(UserLoginRequest request) {
        SiteUser user = siteUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        // 로그인 성공 -> UserLoginResponse로 변환
        UserLoginResponse response = new UserLoginResponse();
        response.setId(user.getId());
        response.setRestaurantName(user.getRestaurantName());
        response.setEmail(user.getEmail());
        response.setTableCount(user.getTableCount());

        return response;
    }
}