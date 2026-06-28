import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CustomerLoginRequest, CustomerService } from '../../services/customer.service';
import { AuthTokenService } from '../../services/auth-token.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginRequest: CustomerLoginRequest = {
    email: '',
    password: ''
  };
  successMessage = '';
  errorMessage = '';
  isLoggingIn = false;

  constructor(
    private customerService: CustomerService,
    private authTokenService: AuthTokenService
  ) { }

  loginCustomer(): void {
    this.successMessage = '';
    this.errorMessage = '';
    this.isLoggingIn = true;

    this.customerService.loginCustomer(this.loginRequest).subscribe({
      next: response => {
        this.authTokenService.saveToken(response.token, response.email);
        this.successMessage = `Welcome ${response.firstName}. Login successful.`;
        this.loginRequest = {
          email: '',
          password: ''
        };
        this.isLoggingIn = false;
      },
      error: error => {
        this.errorMessage = this.getErrorMessage(error);
        this.isLoggingIn = false;
      }
    });
  }

  private getErrorMessage(error: any): string {
    if (error.error && Array.isArray(error.error.errors)) {
      return error.error.errors.join(' ');
    }
    return 'Unable to login. Please try again.';
  }
}
