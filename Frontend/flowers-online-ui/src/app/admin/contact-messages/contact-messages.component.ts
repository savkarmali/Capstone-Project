import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ContactMessageResponse, ContactMessageService } from '../../services/contact-message.service';

@Component({
  selector: 'app-contact-messages',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './contact-messages.component.html',
  styleUrl: './contact-messages.component.css'
})
export class ContactMessagesComponent implements OnInit {
  messages: ContactMessageResponse[] = [];
  isLoading = false;
  errorMessage = '';

  constructor(private contactMessageService: ContactMessageService) { }

  ngOnInit(): void {
    this.loadMessages();
  }

  loadMessages(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.contactMessageService.getMessages().subscribe({
      next: (response: ContactMessageResponse[]) => {
        this.messages = response;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Unable to load contact messages. Please try again.';
        this.isLoading = false;
      }
    });
  }
}
