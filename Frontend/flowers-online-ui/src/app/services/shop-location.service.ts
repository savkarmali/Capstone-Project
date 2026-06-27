import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface ShopLocation {
  id: number;
  shopName: string;
  address: string;
  city: string;
  country: string;
  phoneNumber: string;
}

export interface ShopLocationRequest {
  shopName: string;
  address: string;
  city: string;
  country: string;
  phoneNumber: string;
}

@Injectable({
  providedIn: 'root'
})

export class ShopLocationService {
  private readonly locationsApiUrl = `${environment.apiUrl}/locations`;
  private readonly adminLocationsApiUrl = `${environment.apiUrl}/admin/locations`;

  constructor(private http: HttpClient) { }

  getLocations(): Observable<ShopLocation[]> {
    return this.http.get<ShopLocation[]>(this.locationsApiUrl);
  }

  getAdminLocations(): Observable<ShopLocation[]> {
    return this.http.get<ShopLocation[]>(this.adminLocationsApiUrl);
  }

  createLocation(request: ShopLocationRequest): Observable<ShopLocation> {
    return this.http.post<ShopLocation>(this.adminLocationsApiUrl, request);
  }

  updateLocation(id: number, request: ShopLocationRequest): Observable<ShopLocation> {
    return this.http.put<ShopLocation>(`${this.adminLocationsApiUrl}/${id}`, request);
  }

  deleteLocation(id: number): Observable<void> {
    return this.http.delete<void>(`${this.adminLocationsApiUrl}/${id}`);
  }
}
