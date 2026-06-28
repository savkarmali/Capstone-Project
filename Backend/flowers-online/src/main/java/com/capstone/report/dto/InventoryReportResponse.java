package com.capstone.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryReportResponse {

    private Long productId;
    private String name;
    private String category;
    private Integer stockQuantity;
    private Boolean available;

}
