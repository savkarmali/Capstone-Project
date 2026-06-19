package com.capstone.controller;

import com.capstone.dto.ProductRequest;
import com.capstone.dto.ProductResponse;
import com.capstone.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest){
        ProductResponse productResponse = productService.createProduct(productRequest);
        return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.CREATED);
    }
}
