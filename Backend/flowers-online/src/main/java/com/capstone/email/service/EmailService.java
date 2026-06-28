package com.capstone.email.service;

import com.capstone.order.entity.Order;
import com.capstone.review.entity.Review;

public interface EmailService {

    void sendOrderConfirmation(Order order);

    void sendReviewNotification(Review review);
}
