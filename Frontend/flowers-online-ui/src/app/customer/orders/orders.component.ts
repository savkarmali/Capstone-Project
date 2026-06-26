import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { OrderDetailsResponse, OrderService } from '../../services/order.service';

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersComponent {
  customerEmail = '';
  orders: OrderDetailsResponse[] = [];
  expandedOrderId: number | null = null;
  errorMessage = '';
  isLoading = false;
  hasSearched = false;

  constructor(private orderService: OrderService) { }

  loadOrders(): void {
    this.errorMessage = '';
    this.isLoading = true;
    this.hasSearched = true;

    this.orderService.getOrders(this.customerEmail).subscribe({
      next: orders => {
        this.orders = orders;
        this.isLoading = false;
      },
      error: error => {
        this.errorMessage = this.getErrorMessage(error);
        this.isLoading = false;
      }
    });
  }

  toggleOrder(orderId: number): void {
    this.expandedOrderId = this.expandedOrderId === orderId ? null : orderId;
  }

  private getErrorMessage(error: any): string {
    if (error.error && Array.isArray(error.error.errors)) {
      return error.error.errors.join(' ');
    }
    return 'Unable to load orders. Please try again.';
  }
}
