package com.capstone.survey.service.impl;

import com.capstone.survey.dto.request.LoginRequestDto;
import com.capstone.survey.dto.response.LoginResponseDto;
import com.capstone.survey.entity.User;
import com.capstone.survey.exception.ResourceNotFoundException;
import com.capstone.survey.exception.UnauthorizedException;
import com.capstone.survey.repository.UserRepository;
import com.capstone.survey.security.JwtTokenProvider;
import com.capstone.survey.service.AuthService;
import com.capstone.survey.util.ApiConstants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!Boolean.TRUE.equals(user.getActive())) {
            throw new UnauthorizedException("User account is inactive");
        }

        String token = jwtTokenProvider.generateToken(user);

        return LoginResponseDto.builder()
                .token(token)
                .tokenType(ApiConstants.TOKEN_TYPE_BEARER)
                .userId(user.getId())
                .firstName(user.getFirstName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}