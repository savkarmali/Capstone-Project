package com.capstone.order.service.impl;

import com.capstone.cart.entity.CartItem;
import com.capstone.cart.repository.CartItemRepository;
import com.capstone.order.dto.CheckoutRequest;
import com.capstone.order.dto.OrderDetailsResponse;
import com.capstone.order.dto.OrderItemResponse;
import com.capstone.order.dto.OrderResponse;
import com.capstone.order.entity.Order;
import com.capstone.order.entity.OrderItem;
import com.capstone.order.repository.OrderItemRepository;
import com.capstone.order.repository.OrderRepository;
import com.capstone.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderResponse checkout(CheckoutRequest request) {
        List<CartItem> cartItems = cartItemRepository.findByCustomerEmailIgnoreCase(request.getCustomerEmail());

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty. Add items before checkout");
        }

        BigDecimal orderTotal = cartItems.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setCustomerEmail(request.getCustomerEmail());
        order.setDeliveryName(request.getDeliveryName());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setDeliveryCity(request.getDeliveryCity());
        order.setDeliveryCountry(request.getDeliveryCountry());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setOrderTotal(orderTotal);
        order.setOrderStatus("CONFIRMED");
        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(savedOrder.getId());
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setProductName(cartItem.getProductName());
            orderItem.setImageUrl(cartItem.getImageUrl());
            orderItem.setSelectedSize(cartItem.getSelectedSize());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSubtotal(cartItem.getSubtotal());
            orderItemRepository.save(orderItem);
        }

        cartItemRepository.deleteAll(cartItems);

        return toResponse(savedOrder);
    }

    @Override
    public List<OrderDetailsResponse> getOrdersByCustomerEmail(String customerEmail) {
        return orderRepository.findByCustomerEmailIgnoreCaseOrderByCreatedAtDesc(customerEmail)
                .stream()
                .map(this::toDetailsResponse)
                .collect(Collectors.toList());
    }

    private OrderDetailsResponse toDetailsResponse(Order order) {
        OrderDetailsResponse response = new OrderDetailsResponse();
        response.setOrderId(order.getId());
        response.setCustomerEmail(order.getCustomerEmail());
        response.setDeliveryName(order.getDeliveryName());
        response.setDeliveryAddress(order.getDeliveryAddress());
        response.setDeliveryCity(order.getDeliveryCity());
        response.setDeliveryCountry(order.getDeliveryCountry());
        response.setPhoneNumber(order.getPhoneNumber());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setOrderTotal(order.getOrderTotal());
        response.setOrderStatus(order.getOrderStatus());
        response.setCreatedAt(order.getCreatedAt());
        response.setItems(orderItemRepository.findByOrderId(order.getId())
                .stream()
                .map(this::toItemResponse)
                .collect(Collectors.toList()));
        return response;
    }

    private OrderItemResponse toItemResponse(OrderItem orderItem) {
        OrderItemResponse response = new OrderItemResponse();
        response.setId(orderItem.getId());
        response.setProductId(orderItem.getProductId());
        response.setProductName(orderItem.getProductName());
        response.setImageUrl(orderItem.getImageUrl());
        response.setSelectedSize(orderItem.getSelectedSize());
        response.setPrice(orderItem.getPrice());
        response.setQuantity(orderItem.getQuantity());
        response.setSubtotal(orderItem.getSubtotal());
        return response;
    }

    private OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setCustomerEmail(order.getCustomerEmail());
        response.setOrderTotal(order.getOrderTotal());
        response.setOrderStatus(order.getOrderStatus());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setCreatedAt(order.getCreatedAt());
        response.setMessage("Order placed successfully");
        return response;
    }
}
