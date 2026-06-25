package com.capstone.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class OrderResponse {

    private Long orderId;
    private String customerEmail;
    private BigDecimal orderTotal;
    private String orderStatus;
    private String paymentMethod;
    private LocalDateTime createdAt;
    private String message;
}
