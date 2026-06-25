import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CheckoutRequest, OrderResponse, OrderService } from '../../services/order.service';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent {
  paymentMethods = ['COD', 'Credit Card', 'Debit Card'];
  countries = ['India', 'United States', 'United Kingdom'];
  checkoutRequest: CheckoutRequest = this.getEmptyRequest();
  orderResponse: OrderResponse | null = null;
  errorMessage = '';
  isPlacingOrder = false;

  constructor(private orderService: OrderService) { }

  placeOrder(): void {
    this.errorMessage = '';
    this.orderResponse = null;
    this.isPlacingOrder = true;

    this.orderService.checkout(this.checkoutRequest).subscribe({
      next: response => {
        this.orderResponse = response;
        this.checkoutRequest = this.getEmptyRequest();
        this.isPlacingOrder = false;
      },
      error: error => {
        this.errorMessage = this.getErrorMessage(error);
        this.isPlacingOrder = false;
      }
    });
  }

  private getEmptyRequest(): CheckoutRequest {
    return {
      customerEmail: '',
      deliveryName: '',
      deliveryAddress: '',
      deliveryCity: '',
      deliveryCountry: '',
      phoneNumber: '',
      paymentMethod: ''
    };
  }

  private getErrorMessage(error: any): string {
    if (error.error && Array.isArray(error.error.errors)) {
      return error.error.errors.join(' ');
    }
    return 'Unable to place order. Please try again.';
  }
}
