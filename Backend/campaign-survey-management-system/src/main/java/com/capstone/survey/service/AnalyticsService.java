package com.capstone.survey.service;

import com.capstone.survey.dto.response.AnalyticsDashboardResponseDto;
import com.capstone.survey.dto.response.QuestionAnalyticsResponseDto;
import java.util.List;

public interface AnalyticsService {

    AnalyticsDashboardResponseDto getDashboardAnalytics();

    List<QuestionAnalyticsResponseDto> getSurveyQuestionAnalytics(Long surveyId);
}