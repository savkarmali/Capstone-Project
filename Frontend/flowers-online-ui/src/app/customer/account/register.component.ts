import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CustomerRegistrationRequest, CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  titles = ['Mr', 'Mrs', 'Ms'];
  countries = ['India', 'United States', 'United Kingdom'];
  customer: CustomerRegistrationRequest = this.getEmptyCustomer();
  confirmPassword = '';
  acceptTerms = false;
  successMessage = '';
  errorMessage = '';
  isSaving = false;

  constructor(private customerService: CustomerService) { }

  registerCustomer(): void {
    this.successMessage = '';
    this.errorMessage = '';

    if (this.customer.password !== this.confirmPassword) {
      this.errorMessage = 'Password and confirm password must match.';
      return;
    }

    if (!this.acceptTerms) {
      this.errorMessage = 'Please accept terms and conditions.';
      return;
    }

    this.isSaving = true;
    this.customerService.registerCustomer(this.customer).subscribe({
      next: response => {
        this.successMessage = `Account created successfully for ${response.firstName}.`;
        this.customer = this.getEmptyCustomer();
        this.confirmPassword = '';
        this.acceptTerms = false;
        this.isSaving = false;
      },
      error: error => {
        this.errorMessage = this.getErrorMessage(error);
        this.isSaving = false;
      }
    });
  }

  private getEmptyCustomer(): CustomerRegistrationRequest {
    return {
      title: '',
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      phoneNumber: '',
      city: '',
      country: ''
    };
  }

  private getErrorMessage(error: any): string {
    if (error.error && Array.isArray(error.error.errors)) {
      return error.error.errors.join(' ');
    }
    return 'Unable to create account. Please try again.';
  }
}
