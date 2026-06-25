import { HttpClient } from '@angular/common/http';
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

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private readonly orderApiUrl = `${environment.apiUrl}/orders`;

  constructor(private http: HttpClient) { }

  checkout(request: CheckoutRequest): Observable<OrderResponse> {
    return this.http.post<OrderResponse>(`${this.orderApiUrl}/checkout`, request);
  }
}
