package com.capstone.report.dto;

import java.math.BigDecimal;

public class SalesSummaryResponse {

    private long totalOrders;
    private BigDecimal totalSales;

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }
}
