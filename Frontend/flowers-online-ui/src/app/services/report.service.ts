import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface SalesSummaryResponse {
  totalOrders: number;
  totalSales: number;
}

export interface AdminOrderReportResponse {
  orderId: number;
  customerEmail: string;
  orderTotal: number;
  paymentMethod: string;
  orderStatus: string;
  createdAt: string;
}

export interface InventoryReportResponse {
  productId: number;
  name: string;
  category: string;
  stockQuantity: number;
  available: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class ReportService {
  private readonly reportApiUrl = `${environment.apiUrl}/admin/reports`;

  constructor(private http: HttpClient) { }

  getSalesSummary(): Observable<SalesSummaryResponse> {
    return this.http.get<SalesSummaryResponse>(`${this.reportApiUrl}/sales-summary`);
  }

  getOrderReports(): Observable<AdminOrderReportResponse[]> {
    return this.http.get<AdminOrderReportResponse[]>(`${this.reportApiUrl}/orders`);
  }

  getInventoryReports(): Observable<InventoryReportResponse[]> {
    return this.http.get<InventoryReportResponse[]>(`${this.reportApiUrl}/inventory`);
  }
}
