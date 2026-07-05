package com.capstone.report.service.impl;

import com.capstone.customer.repository.CustomerRepository;
import com.capstone.entity.Product;
import com.capstone.order.dto.AdminOrderReportResponse;
import com.capstone.order.entity.OrderItem;
import com.capstone.order.repository.OrderItemRepository;
import com.capstone.report.dto.*;
import com.capstone.order.entity.Order;
import com.capstone.order.repository.OrderRepository;
import com.capstone.report.service.ReportService;
import com.capstone.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;

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

    @Override
    public List<CategorySalesReportResponse> getCategorySalesReports() {
        Map<String, CategorySalesReportResponse> categoryReportMap =
                new LinkedHashMap<String, CategorySalesReportResponse>();

        List<OrderItem> orderItems = orderItemRepository.findAll();

        for (OrderItem orderItem : orderItems) {
            String category = productRepository.findById(orderItem.getProductId())
                    .map(Product::getCategory)
                    .orElse("Unknown");

            CategorySalesReportResponse report = categoryReportMap.get(category);

            if (report == null) {
                report = new CategorySalesReportResponse();
                report.setCategory(category);
                report.setTotalQuantitySold(0);
                report.setTotalSales(BigDecimal.ZERO);
                categoryReportMap.put(category, report);
            }

            report.setTotalQuantitySold(report.getTotalQuantitySold() + orderItem.getQuantity());
            report.setTotalSales(report.getTotalSales().add(orderItem.getSubtotal()));
        }

        return new ArrayList<CategorySalesReportResponse>(categoryReportMap.values());
    }

    @Override
    public List<ChartReportResponse> getCategorySalesChart() {
        return getCategorySalesReports()
                .stream()
                .map(this::toChartResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DashboardSummaryResponse getDashboardSummary() {
        List<Order> orders = orderRepository.findAll();
        List<Product> products = productRepository.findAll();

        BigDecimal totalRevenue = orders.stream()
                .map(Order::getOrderTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer totalInventory = products.stream()
                .map(Product::getStockQuantity)
                .reduce(0, Integer::sum);

        DashboardSummaryResponse response = new DashboardSummaryResponse();
        response.setTotalProducts((long) products.size());
        response.setTotalOrders((long) orders.size());
        response.setTotalCustomers(customerRepository.count());
        response.setTotalRevenue(totalRevenue);
        response.setTotalInventory(totalInventory);
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

    private InventoryReportResponse toInventoryResponse(Product product) {
        InventoryReportResponse response = new InventoryReportResponse();
        response.setProductId(product.getId());
        response.setName(product.getName());
        response.setCategory(product.getCategory());
        response.setStockQuantity(product.getStockQuantity());
        response.setAvailable(product.getAvailable());
        return response;
    }

    private ChartReportResponse toChartResponse(CategorySalesReportResponse categoryReport) {
        ChartReportResponse response = new ChartReportResponse();
        response.setLabel(categoryReport.getCategory());
        response.setValue(categoryReport.getTotalSales());
        return response;
    }
}
