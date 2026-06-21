package com.capstone.location.controller;

import com.capstone.location.dto.ShopLocationResponse;
import com.capstone.location.service.ShopLocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@CrossOrigin(origins = "http://localhost:4200")
public class ShopLocationController {

    private final ShopLocationService shopLocationService;

    public ShopLocationController(ShopLocationService shopLocationService) {
        this.shopLocationService = shopLocationService;
    }

    @GetMapping
    public ResponseEntity<List<ShopLocationResponse>> getAllLocations() {
        return ResponseEntity.ok(shopLocationService.getAllLocations());
    }
}
