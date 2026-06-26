import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface CheckoutRequest {
  customerEmail: string;
  deliveryName: string;
  deliveryAddress: string;
  deliveryCity: string;
  deliveryCountry: string;
  phoneNumber: string;
  paymentMethod: string;
}

export interface OrderResponse {
  orderId: number;
  customerEmail: string;
  orderTotal: number;
  orderStatus: string;
  paymentMethod: string;
  createdAt: string;
  message: string;
}

export interface OrderItemResponse {
  id: number;
  productId: number;
  productName: string;
  imageUrl: string;
  selectedSize: string;
  price: number;
  quantity: number;
  subtotal: number;
}

export interface OrderDetailsResponse {
  orderId: number;
  customerEmail: string;
  deliveryName: string;
  deliveryAddress: string;
  deliveryCity: string;
  deliveryCountry: string;
  phoneNumber: string;
  paymentMethod: string;
  orderTotal: number;
  orderStatus: string;
  createdAt: string;
  items: OrderItemResponse[];
}

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private readonly orderApiUrl = `${environment.apiUrl}/orders`;

  constructor(private http: HttpClient) { }

  checkout(request: CheckoutRequest): Observable<OrderResponse> {
    return this.http.post<OrderResponse>(`${this.orderApiUrl}/checkout`, request);
  }

  getOrders(customerEmail: string): Observable<OrderDetailsResponse[]> {
    const params = new HttpParams().set('customerEmail', customerEmail);
    return this.http.get<OrderDetailsResponse[]>(this.orderApiUrl, { params });
  }
}
