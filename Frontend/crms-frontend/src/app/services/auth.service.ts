import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from './api.service';
import { tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  constructor(private api: ApiService, private router: Router) {}

  login(email: string, password: string) {
    return this.api.post<any>('/auth/login', { email, password }).pipe(tap(res => localStorage.setItem('token', res.token)));
  }

  logout() {
    localStorage.removeItem('token');
    this.router.navigateByUrl('/login');
  }

  token() {
    return localStorage.getItem('token');
  }

  loggedIn() {
    return !!this.token();
  }
}