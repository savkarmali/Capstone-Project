package com.capstone.service;

import com.capstone.dto.ProductRequest;
import com.capstone.dto.ProductResponse;
import com.capstone.entity.Product;
import com.capstone.repository.ProductRepository;
import com.capstone.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Test
    void createProductShouldSaveAndReturnProductDetails() {

        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductServiceImpl(productRepository);

        ProductRequest request = new ProductRequest();
        request.setName("Rose Bouquet");
        request.setDescription("Fresh red roses");
        request.setCategory("Birthday");
        request.setImageUrl("https://example.com/rose.jpg");
        request.setSmallPrice(new BigDecimal("300.00"));
        request.setMediumPrice(new BigDecimal("500.00"));
        request.setLargePrice(new BigDecimal("800.00"));
        request.setStockQuantity(20);
        request.setAvailable(true);

        Product savedProduct = new Product();
        savedProduct.setId(1L);
        savedProduct.setName(request.getName());
        savedProduct.setDescription(request.getDescription());
        savedProduct.setCategory(request.getCategory());
        savedProduct.setImageUrl(request.getImageUrl());
        savedProduct.setSmallPrice(request.getSmallPrice());
        savedProduct.setMediumPrice(request.getMediumPrice());
        savedProduct.setLargePrice(request.getLargePrice());
        savedProduct.setStockQuantity(request.getStockQuantity());
        savedProduct.setAvailable(request.getAvailable());

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductResponse response = productService.createProduct(request);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptor.capture());

        assertEquals("Rose Bouquet", productCaptor.getValue().getName());
        assertEquals(1L, response.getId());
        assertEquals("Rose Bouquet", response.getName());
        assertEquals(new BigDecimal("300.00"), response.getSmallPrice());
    }
}
