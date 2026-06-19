import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink],
  template: `
    <nav class="navbar navbar-expand navbar-dark bg-primary">
      <div class="container-fluid">
        <a class="navbar-brand" routerLink="/dashboard">CRMS POC</a>
        <div class="navbar-nav">
          <a class="nav-link" routerLink="/dashboard">Dashboard</a>
          <a class="nav-link" routerLink="/surveys">Surveys</a>
          <a class="nav-link" routerLink="/responses">Responses</a>
        </div>
        <button class="btn btn-sm btn-light" (click)="auth.logout()">Logout</button>
      </div>
    </nav>
  `
})
export class NavbarComponent {
  constructor(public auth: AuthService) {}
}