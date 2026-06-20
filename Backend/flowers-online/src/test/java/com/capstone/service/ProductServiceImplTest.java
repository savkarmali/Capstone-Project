package com.capstone.service;

import com.capstone.dto.ProductRequest;
import com.capstone.dto.ProductResponse;
import com.capstone.entity.Product;
import com.capstone.exception.ProductNotFoundException;
import com.capstone.repository.ProductRepository;
import com.capstone.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void updateProductShouldChangeExistingProduct() {
        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductServiceImpl(productRepository);

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Old Bouquet");
        existingProduct.setDescription("Old description");
        existingProduct.setCategory("Birthday");
        existingProduct.setImageUrl("https://example.com/old.jpg");
        existingProduct.setSmallPrice(new BigDecimal("250.00"));
        existingProduct.setStockQuantity(5);
        existingProduct.setAvailable(true);

        ProductRequest request = new ProductRequest();
        request.setName("Updated Bouquet");
        request.setDescription("Updated description");
        request.setCategory("Love");
        request.setImageUrl("https://example.com/new.jpg");
        request.setSmallPrice(new BigDecimal("350.00"));
        request.setMediumPrice(new BigDecimal("550.00"));
        request.setLargePrice(new BigDecimal("850.00"));
        request.setStockQuantity(15);
        request.setAvailable(false);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponse response = productService.updateProduct(1L, request);

        assertEquals("Updated Bouquet", response.getName());
        assertEquals("Love", response.getCategory());
        assertEquals(false, response.getAvailable());
    }

    @Test
    void getProductByIdShouldThrowExceptionWhenProductIsMissing() {
        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductServiceImpl(productRepository);

        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(99L));
    }
}
