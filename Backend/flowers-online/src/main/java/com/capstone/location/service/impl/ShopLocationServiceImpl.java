package com.capstone.location.service.impl;

import com.capstone.exception.ShopLocationNotFoundException;
import com.capstone.location.dto.ShopLocationRequest;
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

    @Override
    public ShopLocationResponse createLocation(ShopLocationRequest request) {
        ShopLocation shopLocation = new ShopLocation();
        copyRequestToEntity(request, shopLocation);
        return toResponse(shopLocationRepository.save(shopLocation));
    }

    @Override
    public ShopLocationResponse updateLocation(Long id, ShopLocationRequest request) {
        ShopLocation shopLocation = shopLocationRepository.findById(id)
                .orElseThrow(() -> new ShopLocationNotFoundException(id));
        copyRequestToEntity(request, shopLocation);
        return toResponse(shopLocationRepository.save(shopLocation));
    }

    @Override
    public void deleteLocation(Long id) {
        ShopLocation shopLocation = shopLocationRepository.findById(id)
                .orElseThrow(() -> new ShopLocationNotFoundException(id));
        shopLocationRepository.delete(shopLocation);
    }

    private void copyRequestToEntity(ShopLocationRequest request, ShopLocation shopLocation) {
        shopLocation.setShopName(request.getShopName());
        shopLocation.setAddress(request.getAddress());
        shopLocation.setCity(request.getCity());
        shopLocation.setCountry(request.getCountry());
        shopLocation.setPhoneNumber(request.getPhoneNumber());
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

