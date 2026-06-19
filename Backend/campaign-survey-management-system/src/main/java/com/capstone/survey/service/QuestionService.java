package com.capstone.survey.service;

import com.capstone.survey.dto.request.QuestionRequestDto;
import com.capstone.survey.dto.response.QuestionResponseDto;
import java.util.List;

public interface QuestionService {

    QuestionResponseDto addQuestion(
            Long surveyId,
            QuestionRequestDto questionRequestDto
    );

    QuestionResponseDto updateQuestion(
            Long questionId,
            QuestionRequestDto questionRequestDto
    );

    void deleteQuestion(Long questionId);

    List<QuestionResponseDto> getQuestionsBySurvey(Long surveyId);

}