import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ApiService {
  private base = 'http://localhost:8080/api';
  constructor(private http: HttpClient) {}

  get<T>(url: string) { return this.http.get<T>(this.base + url); }
  post<T>(url: string, body: unknown) { return this.http.post<T>(this.base + url, body); }
  put<T>(url: string, body: unknown = {}) { return this.http.put<T>(this.base + url, body); }
  delete<T>(url: string) { return this.http.delete<T>(this.base + url); }
}