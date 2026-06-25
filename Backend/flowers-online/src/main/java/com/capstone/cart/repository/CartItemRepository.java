package com.capstone.cart.repository;

import com.capstone.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // You can add custom query methods here if needed
    List<CartItem> findByCustomerEmailIgnoreCase(String customerEmail);
}
