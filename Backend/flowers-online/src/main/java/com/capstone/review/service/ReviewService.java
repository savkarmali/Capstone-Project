package com.capstone.review.service;

import com.capstone.review.dto.ReviewRequest;
import com.capstone.review.dto.ReviewResponse;

public interface ReviewService {

    ReviewResponse saveReview(ReviewRequest request);
}
