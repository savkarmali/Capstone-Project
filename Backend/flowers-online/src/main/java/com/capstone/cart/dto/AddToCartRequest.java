package com.capstone.cart.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {

    @NotBlank(message = "Customer email is required")
    @Email(message = "Enter a valid customer email")
    private String customerEmail;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotBlank(message = "Selected size is required")
    private String selectedSize;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
