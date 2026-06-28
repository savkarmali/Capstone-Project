import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ChartReportResponse, ReportService } from '../../services/report.service';

@Component({
  selector: 'app-chart-report',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './chart-report.component.html',
  styleUrl: './chart-report.component.css'
})
export class ChartReportComponent implements OnInit {
  chartData: ChartReportResponse[] = [];
  isLoading = false;
  errorMessage = '';

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.loadChartData();
  }

  loadChartData(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.reportService.getCategorySalesChart().subscribe({
      next: (response: ChartReportResponse[]) => {
        this.chartData = response;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Unable to load chart report.';
        this.isLoading = false;
      }
    });
  }

  get totalSales(): number {
    return this.chartData.reduce((total, item) => total + item.value, 0);
  }

  getBarWidth(value: number): number {
    const maxValue = Math.max(...this.chartData.map(item => item.value), 0);

    if (maxValue === 0) {
      return 0;
    }

    return Math.round((value / maxValue) * 100);
  }
}
