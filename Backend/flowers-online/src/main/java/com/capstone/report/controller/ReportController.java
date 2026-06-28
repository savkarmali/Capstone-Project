package com.capstone.report.controller;

import com.capstone.order.dto.AdminOrderReportResponse;
import com.capstone.report.dto.InventoryReportResponse;
import com.capstone.report.dto.SalesSummaryResponse;
import com.capstone.report.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reports")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/sales-summary")
    public ResponseEntity<SalesSummaryResponse> getSalesSummary() {
        return ResponseEntity.ok(reportService.getSalesSummary());
    }

    @GetMapping("/orders")
    public ResponseEntity<List<AdminOrderReportResponse>> getOrderReports() {
        return ResponseEntity.ok(reportService.getOrderReports());
    }

    @GetMapping("/inventory")
    public ResponseEntity<List<InventoryReportResponse>> getInventoryReports() {
        return ResponseEntity.ok(reportService.getInventoryReports());
    }
}
