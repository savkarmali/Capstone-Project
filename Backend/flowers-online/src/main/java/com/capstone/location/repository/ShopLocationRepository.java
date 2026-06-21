package com.capstone.location.repository;

import com.capstone.location.entity.ShopLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopLocationRepository extends JpaRepository<ShopLocation, Long> {
}
