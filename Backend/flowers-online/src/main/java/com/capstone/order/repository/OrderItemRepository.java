package com.capstone.order.repository;

import com.capstone.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // You can add custom query methods here if needed
    List<OrderItem> findByOrderId(Long orderId);
}
