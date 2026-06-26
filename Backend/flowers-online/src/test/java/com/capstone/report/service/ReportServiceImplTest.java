package com.capstone.report.service;

import com.capstone.order.entity.Order;
import com.capstone.order.repository.OrderRepository;
import com.capstone.report.dto.SalesSummaryResponse;
import com.capstone.report.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReportServiceImplTest {

    @Test
    void getSalesSummaryShouldReturnTotalOrdersAndSales() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        ReportService reportService = new ReportServiceImpl(orderRepository);

        Order firstOrder = getOrder(1L, "500.00");
        Order secondOrder = getOrder(2L, "300.00");
        when(orderRepository.findAll()).thenReturn(Arrays.asList(firstOrder, secondOrder));

        SalesSummaryResponse response = reportService.getSalesSummary();

        assertEquals(2, response.getTotalOrders());
        assertEquals(new BigDecimal("800.00"), response.getTotalSales());
    }

    private Order getOrder(Long id, String total) {
        Order order = new Order();
        order.setId(id);
        order.setCustomerEmail("customer@example.com");
        order.setOrderTotal(new BigDecimal(total));
        order.setPaymentMethod("COD");
        order.setOrderStatus("CONFIRMED");
        order.setCreatedAt(LocalDateTime.now());
        return order;
    }
}
