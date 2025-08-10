package com.example.likelion_ch.service;

import com.example.likelion_ch.dto.UserRegisterRequest;
import com.example.likelion_ch.dto.UserLoginRequest;
import com.example.likelion_ch.dto.UserLoginResponse;
import com.example.likelion_ch.entity.SiteUser;
import com.example.likelion_ch.repository.SiteUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SiteUserService {

    private final SiteUserRepository siteUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public SiteUserService(SiteUserRepository siteUserRepository) {
        this.siteUserRepository = siteUserRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserLoginResponse registerUser(UserRegisterRequest request) {
        if (siteUserRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        SiteUser user = new SiteUser();
        user.setRestaurantName(request.getRestaurantName());
        user.setRestaurantPw(passwordEncoder.encode(request.getRestaurantPw()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole() != null ? request.getRole() : "CUSTOMER");
        user.setTableCount(request.getTableCount());

        SiteUser savedUser = siteUserRepository.save(user);

        return toUserResponse(savedUser); // Changed here
    }

    public UserLoginResponse login(UserLoginRequest request) {
        SiteUser user = siteUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일이 존재하지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getRestaurantPw())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return toUserResponse(user); // Changed here
    }

    private UserLoginResponse toUserResponse(SiteUser user) {
        UserLoginResponse dto = new UserLoginResponse();
        dto.setId(user.getRestaurantId());
        dto.setRestaurantName(user.getRestaurantName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setTableCount(user.getTableCount());
        return dto;
    }
}