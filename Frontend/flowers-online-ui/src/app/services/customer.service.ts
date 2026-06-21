import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface CustomerRegistrationRequest {
  title: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  phoneNumber: string;
  city: string;
  country: string;
}

export interface CustomerResponse {
  id: number;
  title: string;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  city: string;
  country: string;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private readonly customerApiUrl = `${environment.apiUrl}/customers`;

  constructor(private http: HttpClient) { }

  registerCustomer(request: CustomerRegistrationRequest): Observable<CustomerResponse> {
    return this.http.post<CustomerResponse>(`${this.customerApiUrl}/register`, request);
  }
}
