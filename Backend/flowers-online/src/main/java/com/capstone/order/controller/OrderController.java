package com.capstone.order.controller;

import com.capstone.order.dto.CheckoutRequest;
import com.capstone.order.dto.OrderDetailsResponse;
import com.capstone.order.dto.OrderResponse;
import com.capstone.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(@Valid @RequestBody CheckoutRequest request) {
        OrderResponse response = orderService.checkout(request);
        return new ResponseEntity<OrderResponse>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailsResponse>> getOrdersByCustomerEmail(@RequestParam String customerEmail) {
        return ResponseEntity.ok(orderService.getOrdersByCustomerEmail(customerEmail));
    }
}
