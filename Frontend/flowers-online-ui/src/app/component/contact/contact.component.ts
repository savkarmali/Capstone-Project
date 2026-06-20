import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ContactMessageRequest, ContactMessageService } from '../../services/contact-message.service';

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.css'
})
export class ContactComponent {
  contactMessage: ContactMessageRequest = this.getEmptyMessage();
  successMessage = '';
  errorMessage = '';
  isSending = false;

  constructor(private contactMessageService: ContactMessageService) { }

  sendMessage(): void {
    this.successMessage = '';
    this.errorMessage = '';
    this.isSending = true;

    this.contactMessageService.sendMessage(this.contactMessage).subscribe({
      next: response => {
        this.successMessage = `Thank you ${response.name}. Your message has been sent successfully.`;
        this.contactMessage = this.getEmptyMessage();
        this.isSending = false;
      },
      error: error => {
        this.errorMessage = this.getErrorMessage(error);
        this.isSending = false;
      }
    });
  }

  private getEmptyMessage(): ContactMessageRequest {
    return {
      name: '',
      email: '',
      message: ''
    };
  }

  private getErrorMessage(error: any): string {
    if (error.error && Array.isArray(error.error.errors)) {
      return error.error.errors.join(' ');
    }
    return 'Unable to send message. Please try again.';
  }
}
