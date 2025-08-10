package com.example.likelion_ch.controller;

import com.example.likelion_ch.dto.UserRegisterRequest;
import com.example.likelion_ch.dto.UserLoginRequest;
import com.example.likelion_ch.dto.UserLoginResponse;
import com.example.likelion_ch.service.SiteUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class SiteUserController {

    private final SiteUserService siteUserService;

    public SiteUserController(SiteUserService siteUserService) {
        this.siteUserService = siteUserService;
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<UserLoginResponse> register(@RequestBody UserRegisterRequest request) {
        return ResponseEntity.ok(siteUserService.registerUser(request));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request, HttpSession session) {
        UserLoginResponse response = siteUserService.login(request);
        session.setAttribute("user", response);
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(HttpSession session) {
//        session.invalidate();
//        return ResponseEntity.ok("로그아웃 완료");
//    }
}
