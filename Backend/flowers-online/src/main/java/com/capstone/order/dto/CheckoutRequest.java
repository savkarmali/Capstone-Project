package com.capstone.order.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckoutRequest {

    @NotBlank(message = "Customer email is required")
    @Email(message = "Enter a valid customer email")
    private String customerEmail;

    @NotBlank(message = "Delivery name is required")
    @Size(max = 100, message = "Delivery name must be 100 characters or less")
    private String deliveryName;

    @NotBlank(message = "Delivery address is required")
    @Size(max = 300, message = "Delivery address must be 300 characters or less")
    private String deliveryAddress;

    @NotBlank(message = "Delivery city is required")
    private String deliveryCity;

    @NotBlank(message = "Delivery country is required")
    private String deliveryCountry;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
}
