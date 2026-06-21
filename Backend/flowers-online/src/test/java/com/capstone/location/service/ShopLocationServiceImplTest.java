package com.capstone.location.service;

import com.capstone.location.dto.ShopLocationResponse;
import com.capstone.location.entity.ShopLocation;
import com.capstone.location.repository.ShopLocationRepository;
import com.capstone.location.service.impl.ShopLocationServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShopLocationServiceImplTest {

    @Test
    void getAllLocationsShouldReturnShopLocations() {
        ShopLocationRepository repository = mock(ShopLocationRepository.class);
        ShopLocationService service = new ShopLocationServiceImpl(repository);

        ShopLocation location = new ShopLocation();
        location.setId(1L);
        location.setShopName("Flowers Online - Central Shop");
        location.setAddress("12 Rose Street");
        location.setCity("Bengaluru");
        location.setCountry("India");
        location.setPhoneNumber("9876543210");

        when(repository.findAll()).thenReturn(Arrays.asList(location));

        List<ShopLocationResponse> response = service.getAllLocations();

        assertEquals(1, response.size());
        assertEquals("Flowers Online - Central Shop", response.get(0).getShopName());
        assertEquals("Bengaluru", response.get(0).getCity());
    }
}