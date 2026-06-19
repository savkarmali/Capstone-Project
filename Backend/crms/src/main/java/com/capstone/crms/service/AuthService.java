package com.capstone.crms.service;

import com.capstone.crms.dto.*;
import com.capstone.crms.repository.UserRepository;
import com.capstone.crms.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {
        var user = userRepository.findByEmail(request.email()).orElseThrow(() -> new RuntimeException("Invalid login"));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid login");
        }
        return new LoginResponse(jwtUtil.generate(user.getEmail(), user.getRole()), user.getName(), user.getRole());
    }
}
