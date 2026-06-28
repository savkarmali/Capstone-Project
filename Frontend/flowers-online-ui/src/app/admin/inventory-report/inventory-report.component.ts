import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { InventoryReportResponse, ReportService } from '../../services/report.service';

@Component({
  selector: 'app-inventory-report',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './inventory-report.component.html',
  styleUrl: './inventory-report.component.css'
})
export class InventoryReportComponent implements OnInit {
  inventoryReports: InventoryReportResponse[] = [];
  isLoading = false;
  errorMessage = '';

  constructor(private reportService: ReportService) { }

  ngOnInit(): void {
    this.loadInventoryReports();
  }

  loadInventoryReports(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.reportService.getInventoryReports().subscribe({
      next: (response: InventoryReportResponse[]) => {
        this.inventoryReports = response;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Unable to load inventory report.';
        this.isLoading = false;
      }
    });
  }

  get totalProducts(): number {
    return this.inventoryReports.length;
  }

  get totalStock(): number {
    return this.inventoryReports.reduce((total, product) => total + product.stockQuantity, 0);
  }

  get unavailableProducts(): number {
    return this.inventoryReports.filter(product => !product.available).length;
  }
}
