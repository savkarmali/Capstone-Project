import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CustomerService, CustomerLoginRequest } from '../../services/customer.service';


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

  constructor(private customerService: CustomerService) { }

  loginCustomer(): void {
    this.successMessage = '';
    this.errorMessage = '';
    this.isLoggingIn = true;

    this.customerService.loginCustomer(this.loginRequest).subscribe({
      next: response => {
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
