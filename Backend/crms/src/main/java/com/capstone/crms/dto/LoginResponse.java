package com.capstone.crms.dto;

public record LoginResponse(
        String token,
        String username,
        String role) {
}
