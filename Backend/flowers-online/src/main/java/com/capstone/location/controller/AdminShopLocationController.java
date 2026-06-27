package com.capstone.location.controller;

import com.capstone.location.dto.ShopLocationRequest;
import com.capstone.location.dto.ShopLocationResponse;
import com.capstone.location.service.ShopLocationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/locations")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminShopLocationController {

    private final ShopLocationService shopLocationService;

    public AdminShopLocationController(ShopLocationService shopLocationService) {
        this.shopLocationService = shopLocationService;
    }

    @PostMapping
    public ResponseEntity<ShopLocationResponse> createLocation(
            @Valid @RequestBody ShopLocationRequest request) {
        return new ResponseEntity<ShopLocationResponse>(
                shopLocationService.createLocation(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<ShopLocationResponse>> getAllLocations() {
        return ResponseEntity.ok(shopLocationService.getAllLocations());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShopLocationResponse> updateLocation(
            @PathVariable Long id,
            @Valid @RequestBody ShopLocationRequest request) {
        return ResponseEntity.ok(shopLocationService.updateLocation(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        shopLocationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}