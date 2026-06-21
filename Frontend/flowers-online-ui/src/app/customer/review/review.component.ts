import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ReviewRequest, ReviewService } from '../../services/review.service';

@Component({
  selector: 'app-review',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './review.component.html',
  styleUrl: './review.component.css'
})
export class ReviewComponent {
  ratings = [1, 2, 3, 4, 5];
  review: ReviewRequest = this.getEmptyReview();
  successMessage = '';
  errorMessage = '';
  isSending = false;

  constructor(private reviewService: ReviewService) { }

  sendReview(): void {
    this.successMessage = '';
    this.errorMessage = '';
    this.isSending = true;

    this.reviewService.sendReview(this.review).subscribe({
      next: response => {
        this.successMessage = `Thank you. Your ${response.rating}-star review has been submitted.`;
        this.review = this.getEmptyReview();
        this.isSending = false;
      },
      error: error => {
        this.errorMessage = this.getErrorMessage(error);
        this.isSending = false;
      }
    });
  }

  private getEmptyReview(): ReviewRequest {
    return {
      reviewerEmail: '',
      rating: null,
      reviewMessage: ''
    };
  }

  private getErrorMessage(error: any): string {
    if (error.error && Array.isArray(error.error.errors)) {
      return error.error.errors.join(' ');
    }
    return 'Unable to submit review. Please try again.';
  }
}
