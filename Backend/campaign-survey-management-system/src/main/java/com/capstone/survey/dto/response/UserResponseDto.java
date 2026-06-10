package com.capstone.survey.dto.response;

import com.capstone.survey.enums.RoleName;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserResponseDto {

    private final Long id;

    private final String firstName;

    private final String lastName;

    private final String email;

    private final RoleName role;

    private final Boolean active;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;
}