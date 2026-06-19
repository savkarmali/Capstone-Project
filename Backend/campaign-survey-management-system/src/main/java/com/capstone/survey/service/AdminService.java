package com.capstone.survey.service;

import com.capstone.survey.dto.request.AdminCreateRequestDto;
import com.capstone.survey.dto.response.UserResponseDto;
import java.util.List;

public interface AdminService {

    UserResponseDto createAdmin(AdminCreateRequestDto adminCreateRequestDto);

    List<UserResponseDto> getActiveAdmins();
}