import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
  

export interface ProductRequest {
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

export interface ProductResponse extends ProductRequest {
  id: number;
}

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private readonly productApiUrl = `${environment.apiUrl}/admin/products`;

  constructor(private http: HttpClient) { }

  createProduct(product: ProductRequest): Observable<ProductResponse> {
    return this.http.post<ProductResponse>(this.productApiUrl, product);
  }

  getProducts(): Observable<ProductResponse[]> {
    return this.http.get<ProductResponse[]>(this.productApiUrl);
  }

  getProductById(id: number): Observable<ProductResponse> {
    return this.http.get<ProductResponse>(`${this.productApiUrl}/${id}`);
  }

  updateProduct(id: number, product: ProductRequest): Observable<ProductResponse> {
    return this.http.put<ProductResponse>(`${this.productApiUrl}/${id}`, product);
  }

  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.productApiUrl}/${id}`);
  }
}