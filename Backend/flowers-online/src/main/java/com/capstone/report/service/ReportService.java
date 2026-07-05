package com.capstone.report.service;

import com.capstone.order.dto.AdminOrderReportResponse;
import com.capstone.report.dto.*;

import java.util.List;

public interface ReportService {

    SalesSummaryResponse getSalesSummary();

    List<AdminOrderReportResponse> getOrderReports();

    List<InventoryReportResponse> getInventoryReports();

    List<CategorySalesReportResponse> getCategorySalesReports();

    List<ChartReportResponse> getCategorySalesChart();

    DashboardSummaryResponse getDashboardSummary();
}
