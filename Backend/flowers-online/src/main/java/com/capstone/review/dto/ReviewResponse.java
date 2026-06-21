package com.capstone.review.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewResponse {

    private Long id;
    private String reviewerEmail;
    private Integer rating;
    private String reviewMessage;
    private LocalDateTime createdAt;
}
