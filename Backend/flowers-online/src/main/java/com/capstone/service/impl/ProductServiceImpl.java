package com.capstone.service.impl;

import com.capstone.dto.ProductRequest;
import com.capstone.dto.ProductResponse;
import com.capstone.entity.Product;
import com.capstone.exception.ProductNotFoundException;
import com.capstone.repository.ProductRepository;
import com.capstone.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        copyRequestToProduct(productRequest, product);

        Product saveProduct = productRepository.save(product);
        return toResponse(saveProduct);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = findProduct(id);
        return toResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = findProduct(id);
        copyRequestToProduct(productRequest, product);
        Product updatedProduct = productRepository.save(product);
        return toResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = findProduct(id);
        productRepository.delete(product);
    }

    private Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id : "+ id));
    }

    private void copyRequestToProduct(ProductRequest productRequest, Product product) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        product.setSmallPrice(productRequest.getSmallPrice());
        product.setMediumPrice(productRequest.getMediumPrice());
        product.setLargePrice(productRequest.getLargePrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setAvailable(productRequest.getAvailable());
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
