package com.capstone.survey.service;

import com.capstone.survey.dto.request.SubmitSurveyRequestDto;
import com.capstone.survey.dto.response.SubmittedSurveyResponseDto;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResponseService {

    SubmittedSurveyResponseDto submitSurvey(
            String publicToken,
            SubmitSurveyRequestDto submitSurveyRequestDto
    );

    Page<SubmittedSurveyResponseDto> getResponsesBySurvey(
            Long surveyId,
            Pageable pageable
    );

    Page<SubmittedSurveyResponseDto> getResponsesBySurveyAndDateRange(
            Long surveyId,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );

    SubmittedSurveyResponseDto getResponseById(Long responseId);
}