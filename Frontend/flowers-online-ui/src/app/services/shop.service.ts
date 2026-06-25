import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface ShopProduct {
  id: number;
  name: string;
  description: string;
  category: string;
  imageUrl: string;
  smallPrice: number | null;
  mediumPrice: number | null;
  largePrice: number | null;
  stockQuantity: number;
  available: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class ShopService {
  private readonly shopApiUrl = `${environment.apiUrl}/shop`;

  constructor(private http: HttpClient) { }

  getCategories(): Observable<string[]> {
    return this.http.get<string[]>(`${this.shopApiUrl}/categories`);
  }

  getProducts(category: string, sortBy: string): Observable<ShopProduct[]> {
    let params = new HttpParams().set('sortBy', sortBy);

    if (category) {
      params = params.set('category', category);
    }

    return this.http.get<ShopProduct[]>(`${this.shopApiUrl}/products`, { params });
  }

  
  getProductById(id: number): Observable<ShopProduct> {
    return this.http.get<ShopProduct>(`${this.shopApiUrl}/products/${id}`);
  }
}
