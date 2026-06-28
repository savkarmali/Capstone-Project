package com.capstone.report.service;

import com.capstone.order.dto.AdminOrderReportResponse;
import com.capstone.report.dto.InventoryReportResponse;
import com.capstone.report.dto.SalesSummaryResponse;

import java.util.List;

public interface ReportService {

    SalesSummaryResponse getSalesSummary();

    List<AdminOrderReportResponse> getOrderReports();

    List<InventoryReportResponse> getInventoryReports();
}
