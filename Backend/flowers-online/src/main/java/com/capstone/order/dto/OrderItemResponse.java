package com.capstone.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemResponse {

    private Long id;
    private Long productId;
    private String productName;
    private String imageUrl;
    private String selectedSize;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subtotal;

}
