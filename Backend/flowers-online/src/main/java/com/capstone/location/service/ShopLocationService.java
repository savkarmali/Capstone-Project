package com.capstone.location.service;

import com.capstone.location.dto.ShopLocationRequest;
import com.capstone.location.dto.ShopLocationResponse;

import java.util.List;

public interface ShopLocationService {

    List<ShopLocationResponse> getAllLocations();

    ShopLocationResponse createLocation(ShopLocationRequest request);

    ShopLocationResponse updateLocation(Long id, ShopLocationRequest request);

    void deleteLocation(Long id);
}
