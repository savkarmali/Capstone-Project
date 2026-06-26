package com.capstone.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDetailsResponse {

    private Long orderId;
    private String customerEmail;
    private String deliveryName;
    private String deliveryAddress;
    private String deliveryCity;
    private String deliveryCountry;
    private String phoneNumber;
    private String paymentMethod;
    private BigDecimal orderTotal;
    private String orderStatus;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
}
