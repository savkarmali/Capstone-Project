import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AdminOrderReportResponse, ReportService, SalesSummaryResponse } from '../../services/report.service';

@Component({
  selector: 'app-reports',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reports.component.html',
  styleUrl: './reports.component.css'
})
export class ReportsComponent implements OnInit {
  summary: SalesSummaryResponse | null = null;
  orders: AdminOrderReportResponse[] = [];
  errorMessage = '';
  isLoading = false;

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.loadReports();
  }

  loadReports(): void {
    this.errorMessage = '';
    this.isLoading = true;

    this.reportService.getSalesSummary().subscribe({
      next: summary => this.summary = summary,
      error: () => this.errorMessage = 'Unable to load sales summary.'
    });

    this.reportService.getOrderReports().subscribe({
      next: orders => {
        this.orders = orders;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Unable to load order reports.';
        this.isLoading = false;
      }
    });
  }
}
