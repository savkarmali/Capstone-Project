import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthTokenService } from '../../services/auth-token.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  constructor(
    public authTokenService: AuthTokenService,
    private router: Router
  ) { }

  logout(): void {
    this.authTokenService.logout();
    this.router.navigate(['/account/login']);
  }
}
