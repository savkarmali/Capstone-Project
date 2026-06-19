package com.capstone.survey.service;

import com.capstone.survey.dto.request.LoginRequestDto;
import com.capstone.survey.dto.response.LoginResponseDto;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto loginRequestDto);
}