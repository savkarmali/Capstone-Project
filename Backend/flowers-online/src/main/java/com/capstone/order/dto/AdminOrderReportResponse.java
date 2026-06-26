package com.capstone.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class AdminOrderReportResponse {

    private Long orderId;
    private String customerEmail;
    private BigDecimal orderTotal;
    private String paymentMethod;
    private String orderStatus;
    private LocalDateTime createdAt;
}
