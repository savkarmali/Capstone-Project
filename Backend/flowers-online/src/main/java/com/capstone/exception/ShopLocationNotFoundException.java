package com.capstone.exception;

public class ShopLocationNotFoundException extends RuntimeException {
    public ShopLocationNotFoundException(Long id) {
        super("Shop location not found with id: " + id);
    }
}
