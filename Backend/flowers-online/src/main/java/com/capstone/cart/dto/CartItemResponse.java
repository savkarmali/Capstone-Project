package com.capstone.cart.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CartItemResponse {

    private Long id;
    private String customerEmail;
    private Long productId;
    private String productName;
    private String imageUrl;
    private String selectedSize;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subtotal;
    private LocalDateTime createdAt;
}
