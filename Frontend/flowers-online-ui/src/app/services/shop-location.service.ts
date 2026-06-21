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

@Injectable({
  providedIn: 'root'
})
export class ShopLocationService {
  private readonly locationsApiUrl = `${environment.apiUrl}/locations`;

  constructor(private http: HttpClient) { }

  getLocations(): Observable<ShopLocation[]> {
    return this.http.get<ShopLocation[]>(this.locationsApiUrl);
  }
}
