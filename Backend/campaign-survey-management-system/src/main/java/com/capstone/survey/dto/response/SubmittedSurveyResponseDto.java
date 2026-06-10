package com.capstone.survey.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SubmittedSurveyResponseDto {

    private final Long id;

    private final Long surveyId;

    private final String surveyTitle;

    private final String respondentFirstName;

    private final String respondentLastName;

    private final String respondentEmail;

    private final LocalDateTime submittedAt;

    private final List<AnswerResponseDto> answers;
}