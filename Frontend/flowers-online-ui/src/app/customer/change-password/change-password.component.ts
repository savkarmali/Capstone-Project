import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ChangePasswordRequest, CustomerService } from '../../services/customer.service';

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './change-password.component.html',
  styleUrl: './change-password.component.css'
})
export class ChangePasswordComponent {
  changePasswordRequest: ChangePasswordRequest = this.getEmptyRequest();
  confirmPassword = '';
  successMessage = '';
  errorMessage = '';
  isSaving = false;

  constructor(private customerService: CustomerService) { }

  changePassword(): void {
    this.successMessage = '';
    this.errorMessage = '';

    if (this.changePasswordRequest.newPassword !== this.confirmPassword) {
      this.errorMessage = 'New password and confirm password must match.';
      return;
    }

    this.isSaving = true;
    this.customerService.changePassword(this.changePasswordRequest).subscribe({
      next: response => {
        this.successMessage = response.message;
        this.changePasswordRequest = this.getEmptyRequest();
        this.confirmPassword = '';
        this.isSaving = false;
      },
      error: error => {
        this.errorMessage = this.getErrorMessage(error);
        this.isSaving = false;
      }
    });
  }

  private getEmptyRequest(): ChangePasswordRequest {
    return {
      email: '',
      oldPassword: '',
      newPassword: ''
    };
  }

  private getErrorMessage(error: any): string {
    if (error.error && Array.isArray(error.error.errors)) {
      return error.error.errors.join(' ');
    }
    return 'Unable to change password. Please try again.';
  }
}
