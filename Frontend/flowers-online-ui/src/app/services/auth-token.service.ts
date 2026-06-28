import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthTokenService {
  private readonly tokenKey = 'flowersOnlineJwtToken';
  private readonly emailKey = 'flowersOnlineCustomerEmail';

  saveToken(token: string, email: string): void {
    localStorage.setItem(this.tokenKey, token);
    localStorage.setItem(this.emailKey, email);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  getEmail(): string | null {
    return localStorage.getItem(this.emailKey);
  }

  clearToken(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.emailKey);
  }

  isLoggedIn(): boolean {
    return this.getToken() !== null;
  }
}
