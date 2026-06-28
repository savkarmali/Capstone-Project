package com.capstone.email.service.impl;

import com.capstone.email.service.EmailService;
import com.capstone.order.entity.Order;
import com.capstone.review.entity.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender javaMailSender;

    @Value("${app.mail.enabled}")
    private boolean mailEnabled;

    @Value("${app.mail.admin-email}")
    private String adminEmail;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendOrderConfirmation(Order order) {
        String subject = "Flowers Online - Order Confirmation";
        String body = "Hello " + order.getDeliveryName() + ",\n\n"
                + "Your order has been placed successfully.\n"
                + "Order ID: " + order.getId() + "\n"
                + "Order Total: " + order.getOrderTotal() + "\n"
                + "Payment Method: " + order.getPaymentMethod() + "\n"
                + "Status: " + order.getOrderStatus() + "\n\n"
                + "Thank you for shopping with Flowers Online.";

        sendEmail(order.getCustomerEmail(), subject, body);
    }

    @Override
    public void sendReviewNotification(Review review) {
        String subject = "Flowers Online - New Customer Review";
        String body = "A new review has been submitted.\n\n"
                + "Reviewer Email: " + review.getReviewerEmail() + "\n"
                + "Rating: " + review.getRating() + "\n"
                + "Message: " + review.getReviewMessage();

        sendEmail(adminEmail, subject, body);
    }

    private void sendEmail(String toEmail, String subject, String body) {
        if (!mailEnabled) {
            LOGGER.info("Email sending is disabled. To: {}, Subject: {}", toEmail, subject);
            return;
        }
        if (isPlaceholderMailConfiguration()) {
            LOGGER.warn("Email sending skipped. Please configure spring.mail.username and spring.mail.password.");
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(message);
        }catch (MailAuthenticationException exception) {
            LOGGER.warn("Email authentication failed. Use a valid email address and app password.");
        } catch (Exception exception) {
            LOGGER.warn("Unable to send email to {}. Reason: {}", toEmail, exception.getMessage());
        }
    }

    private boolean isPlaceholderMailConfiguration() {
        return fromEmail == null
                || fromEmail.trim().isEmpty()
                || "your-email@gmail.com".equalsIgnoreCase(fromEmail);
    }
}
