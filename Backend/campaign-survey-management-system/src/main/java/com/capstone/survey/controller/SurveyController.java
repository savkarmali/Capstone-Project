package com.capstone.survey.controller;

import com.capstone.survey.dto.request.SurveyCreateRequestDto;
import com.capstone.survey.dto.request.SurveyUpdateRequestDto;
import com.capstone.survey.dto.response.ApiResponseDto;
import com.capstone.survey.dto.response.SurveyResponseDto;
import com.capstone.survey.enums.SurveyStatus;
import com.capstone.survey.service.SurveyService;
import com.capstone.survey.util.ApiConstants;
import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.SURVEYS_BASE)
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<SurveyResponseDto>> createSurvey(
            @Valid @RequestBody SurveyCreateRequestDto surveyCreateRequestDto,
            Principal principal
    ) {
        SurveyResponseDto surveyResponseDto = surveyService.createSurvey(
                surveyCreateRequestDto,
                principal.getName()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Survey created successfully", surveyResponseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<Page<SurveyResponseDto>>> getSurveys(
            @RequestParam(required = false) SurveyStatus status,
            Pageable pageable
    ) {
        return ResponseEntity.ok(
                ApiResponseDto.success("Surveys fetched successfully", surveyService.getSurveys(status, pageable))
        );
    }

    @GetMapping("/{surveyId}")
    public ResponseEntity<ApiResponseDto<SurveyResponseDto>> getSurveyById(
            @PathVariable Long surveyId
    ) {
        return ResponseEntity.ok(
                ApiResponseDto.success("Survey fetched successfully", surveyService.getSurveyById(surveyId))
        );
    }

    @PutMapping("/{surveyId}")
    public ResponseEntity<ApiResponseDto<SurveyResponseDto>> updateSurvey(
            @PathVariable Long surveyId,
            @Valid @RequestBody SurveyUpdateRequestDto surveyUpdateRequestDto
    ) {
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        "Survey updated successfully",
                        surveyService.updateSurvey(surveyId, surveyUpdateRequestDto)
                )
        );
    }

    @PostMapping("/{surveyId}/publish")
    public ResponseEntity<ApiResponseDto<SurveyResponseDto>> publishSurvey(
            @PathVariable Long surveyId
    ) {
        return ResponseEntity.ok(
                ApiResponseDto.success("Survey published successfully", surveyService.publishSurvey(surveyId))
        );
    }

    @PostMapping("/{surveyId}/close")
    public ResponseEntity<ApiResponseDto<SurveyResponseDto>> closeSurvey(
            @PathVariable Long surveyId
    ) {
        return ResponseEntity.ok(
                ApiResponseDto.success("Survey closed successfully", surveyService.closeSurvey(surveyId))
        );
    }

    @DeleteMapping("/{surveyId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteSurvey(
            @PathVariable Long surveyId
    ) {
        surveyService.deleteSurvey(surveyId);

        return ResponseEntity.ok(
                ApiResponseDto.success("Survey deleted successfully", null)
        );
    }
}