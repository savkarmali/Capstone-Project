package com.capstone.survey.dto.response;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestionAnalyticsResponseDto {

    private final Long questionId;

    private final String questionText;

    private final String questionType;

    private final Map<String, Long> answerDistribution;
}