package com.capstone.survey.service;

import com.capstone.survey.dto.request.SurveyCreateRequestDto;
import com.capstone.survey.dto.request.SurveyUpdateRequestDto;
import com.capstone.survey.dto.response.SurveyResponseDto;
import com.capstone.survey.enums.SurveyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SurveyService {

    SurveyResponseDto createSurvey(
            SurveyCreateRequestDto surveyCreateRequestDto,
            String adminEmail
    );

    Page<SurveyResponseDto> getSurveys(
            SurveyStatus status,
            Pageable pageable
    );

    SurveyResponseDto getSurveyById(Long surveyId);

    SurveyResponseDto updateSurvey(
            Long surveyId,
            SurveyUpdateRequestDto surveyUpdateRequestDto
    );

    SurveyResponseDto publishSurvey(Long surveyId);

    SurveyResponseDto closeSurvey(Long surveyId);

    void deleteSurvey(Long surveyId);

    SurveyResponseDto getPublicSurveyByToken(String publicToken);
}