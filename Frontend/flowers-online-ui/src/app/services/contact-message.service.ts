import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface ContactMessageRequest {
  name: string;
  email: string;
  message: string;
}

export interface ContactMessageResponse extends ContactMessageRequest {
  id: number;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class ContactMessageService {
  private readonly contactApiUrl = `${environment.apiUrl}/contact-messages`;
   private readonly adminContactApiUrl = `${environment.apiUrl}/admin/contact-messages`;

  constructor(private http: HttpClient) { }

  sendMessage(request: ContactMessageRequest): Observable<ContactMessageResponse> {
    return this.http.post<ContactMessageResponse>(this.contactApiUrl, request);
  }
  
  getMessages(): Observable<ContactMessageResponse[]> {
    return this.http.get<ContactMessageResponse[]>(this.adminContactApiUrl);
  }
}