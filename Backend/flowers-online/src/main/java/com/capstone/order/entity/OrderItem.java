package com.capstone.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "order_id", nullable = false)
        private Long orderId;

        @Column(name = "product_id", nullable = false)
        private Long productId;

        @Column(name = "product_name", nullable = false, length = 100)
        private String productName;

        @Column(name = "image_url", nullable = false, length = 500)
        private String imageUrl;

        @Column(name = "selected_size", nullable = false, length = 20)
        private String selectedSize;

        @Column(nullable = false)
        private BigDecimal price;

        @Column(nullable = false)
        private Integer quantity;

        @Column(nullable = false)
        private BigDecimal subtotal;
}
