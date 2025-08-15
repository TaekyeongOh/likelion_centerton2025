package com.example.likelion_ch.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class StoreUpdateRequest {
    private String restaurantName;
    private String restaurantAddress;
    private String shortDescription;
    private String longDescription;
    private List<String> features;
}
