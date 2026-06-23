package com.capstone.repository;

import com.capstone.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByAvailableTrue();

    List<Product> findByCategoryIgnoreCaseAndAvailableTrue(String category);
}
