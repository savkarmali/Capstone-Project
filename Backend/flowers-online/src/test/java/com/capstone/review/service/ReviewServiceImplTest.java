package com.capstone.review.service;

import com.capstone.review.dto.ReviewRequest;
import com.capstone.review.dto.ReviewResponse;
import com.capstone.review.entity.Review;
import com.capstone.review.repository.ReviewRepository;
import com.capstone.review.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

    @Test
    void saveReviewShouldSaveReviewDetails() {
        ReviewRepository repository = mock(ReviewRepository.class);
        ReviewService service = new ReviewServiceImpl(repository);

        ReviewRequest request = new ReviewRequest();
        request.setReviewerEmail("customer@example.com");
        request.setRating(5);
        request.setReviewMessage("Great shopping experience.");

        when(repository.save(any(Review.class))).thenAnswer(invocation -> {
            Review review = invocation.getArgument(0);
            review.setId(1L);
            return review;
        });

        ReviewResponse response = service.saveReview(request);

        ArgumentCaptor<Review> captor = ArgumentCaptor.forClass(Review.class);
        verify(repository).save(captor.capture());

        assertEquals("customer@example.com", captor.getValue().getReviewerEmail());
        assertEquals(5, response.getRating());
        assertEquals(1L, response.getId());
        assertNotNull(response.getCreatedAt());
    }

}