import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { CategorySalesReportResponse, ReportService } from '../../services/report.service';

@Component({
  selector: 'app-category-sales-report',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './category-sales-report.component.html',
  styleUrl: './category-sales-report.component.css'
})
export class CategorySalesReportComponent implements OnInit {
  categoryReports: CategorySalesReportResponse[] = [];
  isLoading = false;
  errorMessage = '';

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.loadCategorySalesReports();
  }

  loadCategorySalesReports(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.reportService.getCategorySalesReports().subscribe({
      next: (response: CategorySalesReportResponse[]) => {
        this.categoryReports = response;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Unable to load category sales report.';
        this.isLoading = false;
      }
    });
  }

  get totalQuantitySold(): number {
    return this.categoryReports.reduce((total, report) => total + report.totalQuantitySold, 0);
  }

  get totalSales(): number {
    return this.categoryReports.reduce((total, report) => total + report.totalSales, 0);
  }
}
