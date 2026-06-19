package com.capstone.survey.controller;

import com.capstone.survey.dto.response.ApiResponseDto;
import com.capstone.survey.dto.response.SubmittedSurveyResponseDto;
import com.capstone.survey.service.ResponseService;
import com.capstone.survey.util.ApiConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.RESPONSES_BASE)
public class ResponseController {

    private final ResponseService responseService;

    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<ApiResponseDto<Page<SubmittedSurveyResponseDto>>> getResponsesBySurveyId(
            @PathVariable Long surveyId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        "Survey responses fetched successfully",
                        responseService.getResponsesBySurvey(surveyId, pageable)
                )
        );
    }

    @GetMapping("/{responseId}")
    public ResponseEntity<ApiResponseDto<SubmittedSurveyResponseDto>> getResponseById(
            @PathVariable Long responseId
    ) {
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        "Survey response fetched successfully",
                        responseService.getResponseById(responseId)
                )
        );
    }
}