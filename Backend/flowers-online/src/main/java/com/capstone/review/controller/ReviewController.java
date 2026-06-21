package com.capstone.review.controller;

import com.capstone.review.dto.ReviewRequest;
import com.capstone.review.dto.ReviewResponse;
import com.capstone.review.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> saveReview(@Valid @RequestBody ReviewRequest request) {
        ReviewResponse response = reviewService.saveReview(request);
        return new ResponseEntity<ReviewResponse>(response, HttpStatus.CREATED);
    }
}