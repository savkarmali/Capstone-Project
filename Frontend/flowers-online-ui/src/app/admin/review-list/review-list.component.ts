import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ReviewResponse, ReviewService } from '../../services/review.service';

@Component({
  selector: 'app-review-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './review-list.component.html',
  styleUrl: './review-list.component.css'
})
export class ReviewListComponent implements OnInit {
  reviews: ReviewResponse[] = [];
  isLoading = false;
  errorMessage = '';

  constructor(private reviewService: ReviewService) { }

  ngOnInit(): void {
    this.loadReviews();
  }

  loadReviews(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.reviewService.getReviews().subscribe({
      next: (response: ReviewResponse[]) => {
        this.reviews = response;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Unable to load customer reviews. Please try again.';
        this.isLoading = false;
      }
    });
  }
}
