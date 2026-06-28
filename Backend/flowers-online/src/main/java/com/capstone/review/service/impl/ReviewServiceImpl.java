package com.capstone.review.service.impl;

import com.capstone.email.service.EmailService;
import com.capstone.review.dto.ReviewRequest;
import com.capstone.review.dto.ReviewResponse;
import com.capstone.review.entity.Review;
import com.capstone.review.repository.ReviewRepository;
import com.capstone.review.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final EmailService emailService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, EmailService emailService) {
        this.reviewRepository = reviewRepository;
        this.emailService = emailService;
    }

    @Override
    public ReviewResponse saveReview(ReviewRequest request) {
        Review review = new Review();
        review.setReviewerEmail(request.getReviewerEmail());
        review.setRating(request.getRating());
        review.setReviewMessage(request.getReviewMessage());
        review.setCreatedAt(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);
        emailService.sendReviewNotification(savedReview);

        return toResponse(savedReview);
    }

    @Override
    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Review::getCreatedAt).reversed())
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ReviewResponse toResponse(Review review) {
        ReviewResponse response = new ReviewResponse();
        response.setId(review.getId());
        response.setReviewerEmail(review.getReviewerEmail());
        response.setRating(review.getRating());
        response.setReviewMessage(review.getReviewMessage());
        response.setCreatedAt(review.getCreatedAt());
        return response;
    }
}
