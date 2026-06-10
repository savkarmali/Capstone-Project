package com.capstone.survey.dto.response;

import com.capstone.survey.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponseDto {

    private final String token;

    private final String tokenType;

    private final Long userId;

    private final String firstName;

    private final String email;

    private final RoleName role;
}