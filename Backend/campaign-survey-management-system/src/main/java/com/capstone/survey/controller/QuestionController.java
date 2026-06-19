package com.capstone.survey.controller;

import com.capstone.survey.dto.request.QuestionRequestDto;
import com.capstone.survey.dto.response.ApiResponseDto;
import com.capstone.survey.dto.response.QuestionResponseDto;
import com.capstone.survey.service.QuestionService;
import com.capstone.survey.util.ApiConstants;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.QUESTIONS_BASE)
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/survey/{surveyId}")
    public ResponseEntity<ApiResponseDto<QuestionResponseDto>> addQuestion(
            @PathVariable Long surveyId,
            @Valid @RequestBody QuestionRequestDto questionRequestDto
    ) {
        QuestionResponseDto responseDto = questionService.addQuestion(surveyId, questionRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Question added successfully", responseDto));
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<ApiResponseDto<QuestionResponseDto>> updateQuestion(
            @PathVariable Long questionId,
            @Valid @RequestBody QuestionRequestDto questionRequestDto
    ) {
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        "Question updated successfully",
                        questionService.updateQuestion(questionId, questionRequestDto)
                )
        );
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteQuestion(
            @PathVariable Long questionId
    ) {
        questionService.deleteQuestion(questionId);

        return ResponseEntity.ok(
                ApiResponseDto.success("Question deleted successfully", null)
        );
    }

    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<ApiResponseDto<List<QuestionResponseDto>>> getQuestionsBySurveyId(
            @PathVariable Long surveyId
    ) {
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        "Questions fetched successfully",
                        questionService.getQuestionsBySurvey(surveyId)
                )
        );
    }
}