package com.capstone.location.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopLocationResponse {

    private Long id;
    private String shopName;
    private String address;
    private String city;
    private String country;
    private String phoneNumber;
}
