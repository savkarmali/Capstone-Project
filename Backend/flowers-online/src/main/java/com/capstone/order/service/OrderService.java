package com.capstone.order.service;

import com.capstone.order.dto.CheckoutRequest;
import com.capstone.order.dto.OrderDetailsResponse;
import com.capstone.order.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse checkout(CheckoutRequest request);

    List<OrderDetailsResponse> getOrdersByCustomerEmail(String customerEmail);
}
