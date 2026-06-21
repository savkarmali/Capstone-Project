package com.capstone.location.service.impl;

import com.capstone.location.dto.ShopLocationResponse;
import com.capstone.location.entity.ShopLocation;
import com.capstone.location.repository.ShopLocationRepository;
import com.capstone.location.service.ShopLocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopLocationServiceImpl implements ShopLocationService {

    private final ShopLocationRepository shopLocationRepository;

    public ShopLocationServiceImpl(ShopLocationRepository shopLocationRepository) {
        this.shopLocationRepository = shopLocationRepository;
    }

    @Override
    public List<ShopLocationResponse> getAllLocations() {
        return shopLocationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ShopLocationResponse toResponse(ShopLocation shopLocation) {
        ShopLocationResponse response = new ShopLocationResponse();
        response.setId(shopLocation.getId());
        response.setShopName(shopLocation.getShopName());
        response.setAddress(shopLocation.getAddress());
        response.setCity(shopLocation.getCity());
        response.setCountry(shopLocation.getCountry());
        response.setPhoneNumber(shopLocation.getPhoneNumber());
        return response;
    }
}
