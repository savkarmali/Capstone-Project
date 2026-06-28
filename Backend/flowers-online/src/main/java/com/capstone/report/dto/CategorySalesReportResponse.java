package com.capstone.report.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CategorySalesReportResponse {

    private String category;
    private Integer totalQuantitySold;
    private BigDecimal totalSales;
}
