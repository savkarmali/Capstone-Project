package com.capstone.survey.controller;

import com.capstone.survey.dto.request.AdminCreateRequestDto;
import com.capstone.survey.dto.response.ApiResponseDto;
import com.capstone.survey.dto.response.UserResponseDto;
import com.capstone.survey.service.AdminService;
import com.capstone.survey.util.ApiConstants;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.ADMIN_BASE + "/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<UserResponseDto>> createAdmin(
            @Valid @RequestBody AdminCreateRequestDto adminCreateRequestDto
    ) {
        UserResponseDto userResponseDto = adminService.createAdmin(adminCreateRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Admin created successfully", userResponseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<UserResponseDto>>> getActiveAdmins() {
        return ResponseEntity.ok(
                ApiResponseDto.success("Active admins fetched successfully", adminService.getActiveAdmins())
        );
    }
}