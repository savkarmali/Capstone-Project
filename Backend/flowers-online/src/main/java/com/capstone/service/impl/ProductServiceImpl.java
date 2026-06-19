package com.capstone.service.impl;

import com.capstone.dto.ProductRequest;
import com.capstone.dto.ProductResponse;
import com.capstone.entity.Product;
import com.capstone.repository.ProductRepository;
import com.capstone.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        product.setSmallPrice(productRequest.getSmallPrice());
        product.setMediumPrice(productRequest.getMediumPrice());
        product.setLargePrice(productRequest.getLargePrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setAvailable(productRequest.getAvailable());

        Product saveProduct = productRepository.save(product);
        return toResponse(saveProduct);
    }

    private ProductResponse toResponse(Product saveProduct) {
        ProductResponse response = new ProductResponse();
        response.setId(saveProduct.getId());
        response.setName(saveProduct.getName());
        response.setDescription(saveProduct.getDescription());
        response.setCategory(saveProduct.getCategory());
        response.setImageUrl(saveProduct.getImageUrl());
        response.setSmallPrice(saveProduct.getSmallPrice());
        response.setMediumPrice(saveProduct.getMediumPrice());
        response.setLargePrice(saveProduct.getLargePrice());
        response.setStockQuantity(saveProduct.getStockQuantity());
        response.setAvailable(saveProduct.getAvailable());
        return response;

    }
}
