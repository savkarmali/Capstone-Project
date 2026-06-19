package com.capstone.survey.controller;

import com.capstone.survey.dto.request.LoginRequestDto;
import com.capstone.survey.dto.response.ApiResponseDto;
import com.capstone.survey.dto.response.LoginResponseDto;
import com.capstone.survey.service.AuthService;
import com.capstone.survey.util.ApiConstants;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.AUTH_BASE)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto
    ) {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);

        return ResponseEntity.ok(
                ApiResponseDto.success("Login successful", loginResponseDto)
        );
    }
}