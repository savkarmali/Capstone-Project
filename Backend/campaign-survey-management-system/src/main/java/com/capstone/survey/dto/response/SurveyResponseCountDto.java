package com.capstone.survey.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SurveyResponseCountDto {

    private final Long surveyId;

    private final String surveyTitle;

    private final long responseCount;
}