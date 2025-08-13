package com.example.likelion_ch.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRegisterStep2Request {
    private String restaurantName;
    private String restaurantAddress;
    private String description;
    private Integer tableCount;
    private List<String> features;
}