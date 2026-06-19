package com.capstone.service;

import com.capstone.dto.ProductRequest;
import com.capstone.dto.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);
}
