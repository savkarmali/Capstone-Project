package com.capstone.survey.mapper;

import com.capstone.survey.dto.response.UserResponseDto;
import com.capstone.survey.entity.User;

public final class UserMapper {

    private UserMapper() {
    }

    public static UserResponseDto toUserResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}