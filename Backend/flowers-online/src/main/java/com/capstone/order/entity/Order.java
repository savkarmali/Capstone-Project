package com.capstone.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_email", nullable = false, length = 150)
    private String customerEmail;

    @Column(name = "delivery_name", nullable = false, length = 100)
    private String deliveryName;

    @Column(name = "delivery_address", nullable = false, length = 300)
    private String deliveryAddress;

    @Column(name = "delivery_city", nullable = false, length = 100)
    private String deliveryCity;

    @Column(name = "delivery_country", nullable = false, length = 100)
    private String deliveryCountry;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "payment_method", nullable = false, length = 30)
    private String paymentMethod;

    @Column(name = "order_total", nullable = false)
    private BigDecimal orderTotal;

    @Column(name = "order_status", nullable = false, length = 30)
    private String orderStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}
