package com.capstone.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name must be 100 characters or less")
    private String name;


    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must be 500 characters or less")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Image URL is required")
    @Size(max = 500, message = "Image URL must be 500 characters or less")
    private String imageUrl;

    @DecimalMin(value = "0.01", message = "Small price must be greater than 0")
    private BigDecimal smallPrice;

    @DecimalMin(value = "0.01", message = "Medium price must be greater than 0")
    private BigDecimal mediumPrice;

    @DecimalMin(value = "0.01", message = "Large price must be greater than 0")
    private BigDecimal largePrice;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @NotNull(message = "Availability is required")
    private Boolean available;

    @AssertTrue(message = "Enter at least one price: small, medium, or large")
    public boolean isAtLeastOnePricePresent() {
        return smallPrice != null || mediumPrice != null || largePrice != null;
    }

}
