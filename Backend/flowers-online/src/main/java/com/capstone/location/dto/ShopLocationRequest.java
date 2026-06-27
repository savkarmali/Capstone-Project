package com.capstone.location.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopLocationRequest {

    @NotBlank(message = "Shop name is required")
    @Size(max = 100, message = "Shop name must be 100 characters or less")
    private String shopName;

    @NotBlank(message = "Address is required")
    @Size(max = 300, message = "Address must be 300 characters or less")
    private String address;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must be 100 characters or less")
    private String city;

    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country must be 100 characters or less")
    private String country;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain 10 digits")
    private String phoneNumber;
}
