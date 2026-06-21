import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface ReviewRequest {
  reviewerEmail: string;
  rating: number | null;
  reviewMessage: string;
}

export interface ReviewResponse {
  id: number;
  reviewerEmail: string;
  rating: number;
  reviewMessage: string;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  private readonly reviewApiUrl = `${environment.apiUrl}/reviews`;

  constructor(private http: HttpClient) { }

  sendReview(request: ReviewRequest): Observable<ReviewResponse> {
    return this.http.post<ReviewResponse>(this.reviewApiUrl, request);
  }
}
