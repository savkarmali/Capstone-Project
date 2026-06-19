package com.capstone.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, length = 100)
        private String name;

        @Column(nullable = false, length = 500)
        private String description;

        @Column(nullable = false, length = 50)
        private String category;

        @Column(name = "image_url", nullable = false, length = 500)
        private String imageUrl;

        @Column(name = "small_price")
        private BigDecimal smallPrice;

        @Column(name = "medium_price")
        private BigDecimal mediumPrice;

        @Column(name = "large_price")
        private BigDecimal largePrice;

        @Column(name = "stock_quantity", nullable = false)
        private Integer stockQuantity;

        @Column(nullable = false)
        private Boolean available;

}
