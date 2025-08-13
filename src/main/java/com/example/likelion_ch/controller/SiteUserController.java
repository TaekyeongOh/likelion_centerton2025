package com.example.likelion_ch.controller;

import com.example.likelion_ch.dto.UserLoginRequest;
import com.example.likelion_ch.dto.UserLoginResponse;
import com.example.likelion_ch.dto.UserRegisterStep1Request;
import com.example.likelion_ch.dto.UserRegisterStep2Request;
import com.example.likelion_ch.entity.SiteUser; // SiteUser 엔티티 import
import com.example.likelion_ch.service.SiteUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class SiteUserController {

    private final SiteUserService siteUserService;

    public SiteUserController(SiteUserService siteUserService) {
        this.siteUserService = siteUserService;
    }

    // 1단계 회원가입: SiteUser를 반환하도록 수정
    @PostMapping("/register/step1")
    public ResponseEntity<SiteUser> registerStep1(@RequestBody UserRegisterStep1Request request) {
        SiteUser newUser = siteUserService.registerStep1(request);
        return ResponseEntity.ok(newUser);
    }

    // 2단계 회원가입: SiteUser를 반환하도록 수정
    @PostMapping("/register/step2/{userId}")
    public ResponseEntity<?> registerStep2(@PathVariable Long userId,
                                           @RequestBody UserRegisterStep2Request request) {
        SiteUser updatedUser = siteUserService.registerStep2(userId, request);
        return ResponseEntity.ok(updatedUser);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        try {
            UserLoginResponse response = siteUserService.login(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}