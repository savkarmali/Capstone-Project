package com.capstone.report.service.impl;

import com.capstone.order.dto.AdminOrderReportResponse;
import com.capstone.report.dto.SalesSummaryResponse;
import com.capstone.order.entity.Order;
import com.capstone.order.repository.OrderRepository;
import com.capstone.report.service.ReportService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final OrderRepository orderRepository;

    public ReportServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public SalesSummaryResponse getSalesSummary() {
        List<Order> orders = orderRepository.findAll();
        BigDecimal totalSales = orders.stream()
                .map(Order::getOrderTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        SalesSummaryResponse response = new SalesSummaryResponse();
        response.setTotalOrders(orders.size());
        response.setTotalSales(totalSales);
        return response;
    }

    @Override
    public List<AdminOrderReportResponse> getOrderReports() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private AdminOrderReportResponse toResponse(Order order) {
        AdminOrderReportResponse response = new AdminOrderReportResponse();
        response.setOrderId(order.getId());
        response.setCustomerEmail(order.getCustomerEmail());
        response.setOrderTotal(order.getOrderTotal());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setOrderStatus(order.getOrderStatus());
        response.setCreatedAt(order.getCreatedAt());
        return response;
    }
}
