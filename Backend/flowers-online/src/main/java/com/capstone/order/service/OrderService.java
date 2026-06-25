package com.capstone.order.service;

import com.capstone.order.dto.CheckoutRequest;
import com.capstone.order.dto.OrderResponse;

public interface OrderService {

    OrderResponse checkout(CheckoutRequest request);
}
