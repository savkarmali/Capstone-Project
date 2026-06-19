package com.capstone.survey.controller;

import com.capstone.survey.dto.request.SubmitSurveyRequestDto;
import com.capstone.survey.dto.response.ApiResponseDto;
import com.capstone.survey.dto.response.SubmittedSurveyResponseDto;
import com.capstone.survey.dto.response.SurveyResponseDto;
import com.capstone.survey.service.ResponseService;
import com.capstone.survey.service.SurveyService;
import com.capstone.survey.util.ApiConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.PUBLIC_BASE + "/surveys")
public class PublicSurveyController {

    private final SurveyService surveyService;
    private final ResponseService responseService;

    public PublicSurveyController(
            SurveyService surveyService,
            ResponseService responseService
    ) {
        this.surveyService = surveyService;
        this.responseService = responseService;
    }

    @GetMapping("/{publicToken}")
    public ResponseEntity<ApiResponseDto<SurveyResponseDto>> getPublicSurvey(
            @PathVariable String publicToken
    ) {
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        "Public survey fetched successfully",
                        surveyService.getPublicSurveyByToken(publicToken)
                )
        );
    }

    @PostMapping("/{publicToken}/responses")
    public ResponseEntity<ApiResponseDto<SubmittedSurveyResponseDto>> submitSurveyResponse(
            @PathVariable String publicToken,
            @Valid @RequestBody SubmitSurveyRequestDto requestDto
    ) {
        SubmittedSurveyResponseDto responseDto = responseService.submitSurvey(
                publicToken,
                requestDto
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Survey response submitted successfully", responseDto));
    }
}