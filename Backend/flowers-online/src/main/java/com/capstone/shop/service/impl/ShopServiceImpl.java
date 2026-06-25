package com.capstone.shop.service.impl;

import com.capstone.dto.ProductResponse;
import com.capstone.entity.Product;
import com.capstone.exception.ProductNotFoundException;
import com.capstone.repository.ProductRepository;
import com.capstone.shop.service.ShopService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {

    private final ProductRepository productRepository;

    public ShopServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<String> getCategories() {
        return Arrays.asList(
                "All",
                "Birthday",
                "Love",
                "Marriage",
                "Grand-Opening",
                "Sympathy",
                "Get-well-soon"
        );
    }

    @Override
    public List<ProductResponse> getProducts(String category, String sortBy) {
        List<Product> products;

        if (category == null || category.trim().isEmpty() || "All".equalsIgnoreCase(category)) {
            products = productRepository.findByAvailableTrue();
        } else {
            products = productRepository.findByCategoryIgnoreCaseAndAvailableTrue(category);
        }

        return products.stream()
                .sorted(getComparator(sortBy))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        if (!Boolean.TRUE.equals(product.getAvailable())) {
            throw new ProductNotFoundException("Product not available with id: " + id);
        }

        return toResponse(product);
    }

    private Comparator<Product> getComparator(String sortBy) {
        if ("price-high-to-low".equalsIgnoreCase(sortBy)) {
            return Comparator.comparing(this::getLowestPrice, Comparator.nullsLast(BigDecimal::compareTo)).reversed();
        }

        if ("price-low-to-high".equalsIgnoreCase(sortBy)) {
            return Comparator.comparing(this::getLowestPrice, Comparator.nullsLast(BigDecimal::compareTo));
        }

        if ("name".equalsIgnoreCase(sortBy)) {
            return Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER);
        }

        return Comparator.comparing(Product::getId, Comparator.nullsLast(Long::compareTo)).reversed();
    }

    private BigDecimal getLowestPrice(Product product) {
        return Arrays.asList(product.getSmallPrice(), product.getMediumPrice(), product.getLargePrice())
                .stream()
                .filter(price -> price != null)
                .min(BigDecimal::compareTo)
                .orElse(null);
    }

    private ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setCategory(product.getCategory());
        response.setImageUrl(product.getImageUrl());
        response.setSmallPrice(product.getSmallPrice());
        response.setMediumPrice(product.getMediumPrice());
        response.setLargePrice(product.getLargePrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setAvailable(product.getAvailable());
        return response;
    }
}
