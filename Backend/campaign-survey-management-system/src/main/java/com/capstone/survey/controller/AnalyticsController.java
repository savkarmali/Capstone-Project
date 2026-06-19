package com.capstone.survey.controller;

import com.capstone.survey.dto.response.AnalyticsDashboardResponseDto;
import com.capstone.survey.dto.response.ApiResponseDto;
import com.capstone.survey.dto.response.QuestionAnalyticsResponseDto;
import com.capstone.survey.service.AnalyticsService;
import com.capstone.survey.util.ApiConstants;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.ANALYTICS_BASE)
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponseDto<AnalyticsDashboardResponseDto>> getDashboardAnalytics() {
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        "Dashboard analytics fetched successfully",
                        analyticsService.getDashboardAnalytics()
                )
        );
    }

    @GetMapping("/survey/{surveyId}/questions")
    public ResponseEntity<ApiResponseDto<List<QuestionAnalyticsResponseDto>>> getSurveyQuestionAnalytics(
            @PathVariable Long surveyId
    ) {
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        "Survey question analytics fetched successfully",
                        analyticsService.getSurveyQuestionAnalytics(surveyId)
                )
        );
    }
}