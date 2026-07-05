package com.capstone.report.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DashboardSummaryResponse {

    private Long totalProducts;
    private Long totalOrders;
    private Long totalCustomers;
    private BigDecimal totalRevenue;
    private Integer totalInventory;
}
