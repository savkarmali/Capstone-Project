package com.capstone.shop.service;

import com.capstone.dto.ProductResponse;
import com.capstone.entity.Product;

import java.util.List;

public interface ShopService {

    List<String> getCategories();

    List<ProductResponse> getProducts(String category, String sortBy);

    ProductResponse getProductById(Long id);
}
