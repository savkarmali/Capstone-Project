import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface AddToCartRequest {
  customerEmail: string;
  productId: number;
  selectedSize: string;
  quantity: number;
}

export interface CartItemResponse {
  id: number;
  customerEmail: string;
  productId: number;
  productName: string;
  imageUrl: string;
  selectedSize: string;
  price: number;
  quantity: number;
  subtotal: number;
  createdAt: string;
}

export interface CartSummaryResponse {
  customerEmail: string;
  items: CartItemResponse[];
  cartTotal: number;
}

export interface UpdateCartItemRequest {
  quantity: number;
}

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private readonly cartApiUrl = `${environment.apiUrl}/cart`;

  constructor(private http: HttpClient) { }

  addToCart(request: AddToCartRequest): Observable<CartItemResponse> {
    return this.http.post<CartItemResponse>(`${this.cartApiUrl}/items`, request);
  }

  getCart(customerEmail: string): Observable<CartSummaryResponse> {
    const params = new HttpParams().set('customerEmail', customerEmail);
    return this.http.get<CartSummaryResponse>(`${this.cartApiUrl}/items`, { params });
  }

  updateCartItem(cartItemId: number, request: UpdateCartItemRequest): Observable<CartItemResponse> {
    return this.http.put<CartItemResponse>(`${this.cartApiUrl}/items/${cartItemId}`, request);
  }

  deleteCartItem(cartItemId: number): Observable<void> {
    return this.http.delete<void>(`${this.cartApiUrl}/items/${cartItemId}`);
  }
}
