package com.example.likelion_ch.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    private String restaurantName;
    private String restaurantPw;
    private String email;
    private String role;
    private Integer tableCount;
}