package com.capstone.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reviewer_email", nullable = false, length = 150)
    private String reviewerEmail;

    @Column(nullable = false)
    private Integer rating;

    @Column(name = "review_message", nullable = false, length = 1000)
    private String reviewMessage;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
