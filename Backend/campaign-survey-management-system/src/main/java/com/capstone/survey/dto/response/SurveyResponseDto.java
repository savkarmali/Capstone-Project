package com.capstone.survey.dto.response;

import com.capstone.survey.enums.SurveyStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SurveyResponseDto {

    private final Long id;

    private final String title;

    private final String description;

    private final SurveyStatus status;

    private final String publicToken;

    private final String surveyUrl;

    private final LocalDate startDate;

    private final LocalDate endDate;

    private final Long createdById;

    private final String createdByName;

    private final LocalDateTime createdAt;

    private final LocalDateTime publishedAt;

    private final LocalDateTime closedAt;

    private final LocalDateTime updatedAt;

    private final long responseCount;

    private final List<QuestionResponseDto> questions;
}