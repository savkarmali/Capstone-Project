import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CartItemResponse, CartService, CartSummaryResponse } from '../../services/cart.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {
  customerEmail = '';
  cart: CartSummaryResponse | null = null;
  successMessage = '';
  errorMessage = '';
  isLoading = false;

  constructor(private cartService: CartService) { }

  loadCart(): void {
    this.successMessage = '';
    this.errorMessage = '';
    this.isLoading = true;

    this.cartService.getCart(this.customerEmail).subscribe({
      next: response => {
        this.cart = response;
        this.isLoading = false;
      },
      error: error => {
        this.errorMessage = this.getErrorMessage(error);
        this.isLoading = false;
      }
    });
  }

  updateQuantity(item: CartItemResponse): void {
    this.successMessage = '';
    this.errorMessage = '';

    this.cartService.updateCartItem(item.id, { quantity: item.quantity }).subscribe({
      next: () => {
        this.successMessage = 'Cart item updated successfully.';
        this.loadCart();
      },
      error: error => this.errorMessage = this.getErrorMessage(error)
    });
  }

  removeItem(item: CartItemResponse): void {
    const shouldRemove = confirm(`Remove "${item.productName}" from cart?`);
    if (!shouldRemove) {
      return;
    }

    this.successMessage = '';
    this.errorMessage = '';

    this.cartService.deleteCartItem(item.id).subscribe({
      next: () => {
        this.successMessage = 'Cart item removed successfully.';
        this.loadCart();
      },
      error: error => this.errorMessage = this.getErrorMessage(error)
    });
  }

  private getErrorMessage(error: any): string {
    if (error.error && Array.isArray(error.error.errors)) {
      return error.error.errors.join(' ');
    }
    return 'Unable to process cart request. Please try again.';
  }
}
