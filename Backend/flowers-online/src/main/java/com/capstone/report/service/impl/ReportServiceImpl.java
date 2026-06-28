package com.capstone.report.service.impl;

import com.capstone.entity.Product;
import com.capstone.order.dto.AdminOrderReportResponse;
import com.capstone.report.dto.InventoryReportResponse;
import com.capstone.report.dto.SalesSummaryResponse;
import com.capstone.order.entity.Order;
import com.capstone.order.repository.OrderRepository;
import com.capstone.report.service.ReportService;
import com.capstone.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

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

    @Override
    public List<InventoryReportResponse> getInventoryReports() {
        return productRepository.findAll()
                .stream()
                .map(this::toInventoryResponse)
                .collect(Collectors.toList());
    }

    private InventoryReportResponse toInventoryResponse(Product product) {
        InventoryReportResponse response = new InventoryReportResponse();
        response.setProductId(product.getId());
        response.setName(product.getName());
        response.setCategory(product.getCategory());
        response.setStockQuantity(product.getStockQuantity());
        response.setAvailable(product.getAvailable());
        return response;
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
