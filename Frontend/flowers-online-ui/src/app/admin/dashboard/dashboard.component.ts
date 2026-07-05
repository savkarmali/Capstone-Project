import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { DashboardSummaryResponse, ReportService } from '../../services/report.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  summary: DashboardSummaryResponse | null = null;
  errorMessage = '';
  isLoading = false;

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.loadDashboard();
  }

  loadDashboard(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.reportService.getDashboardSummary().subscribe({
      next: response => {
        this.summary = response;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Unable to load dashboard summary.';
        this.isLoading = false;
      }
    });
  }
}
