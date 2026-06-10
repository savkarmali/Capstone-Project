package com.capstone.survey.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AnalyticsResponseDto {

    private final long totalSurveys;

    private final long draftSurveys;

    private final long publishedSurveys;

    private final long closedSurveys;

    private final long totalResponses;

    private final List<SurveyResponseCountDto> responsesBySurvey;
}