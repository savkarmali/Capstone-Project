package com.capstone.review.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequest {

    @NotBlank(message = "Reviewer email is required")
    @Email(message = "Enter a valid reviewer email")
    @Size(max = 150, message = "Reviewer email must be 150 characters or less")
    private String reviewerEmail;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not be more than 5")
    private Integer rating;

    @NotBlank(message = "Review message is required")
    @Size(max = 1000, message = "Review message must be 1000 characters or less")
    private String reviewMessage;
}
