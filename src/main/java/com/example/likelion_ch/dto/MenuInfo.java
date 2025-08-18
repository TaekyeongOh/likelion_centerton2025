package com.example.likelion_ch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class MenuInfo {
    private String menuName;
    private String shortDescription;
    private BigDecimal menuPrice;
}