package com.capstone.review.service;

import com.capstone.review.dto.ReviewRequest;
import com.capstone.review.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {

    ReviewResponse saveReview(ReviewRequest request);

    List<ReviewResponse> getAllReviews();
}
