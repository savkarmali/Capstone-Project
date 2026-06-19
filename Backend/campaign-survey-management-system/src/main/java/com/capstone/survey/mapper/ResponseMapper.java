package com.capstone.survey.mapper;

import com.capstone.survey.dto.response.AnswerResponseDto;
import com.capstone.survey.dto.response.SubmittedSurveyResponseDto;
import com.capstone.survey.entity.Answer;
import com.capstone.survey.entity.SurveyResponse;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ResponseMapper {

    private ResponseMapper() {
    }

    public static SubmittedSurveyResponseDto toSubmittedSurveyResponseDto(
            SurveyResponse surveyResponse
    ) {
        return SubmittedSurveyResponseDto.builder()
                .id(surveyResponse.getId())
                .surveyId(surveyResponse.getSurvey().getId())
                .surveyTitle(surveyResponse.getSurvey().getTitle())
                .respondentFirstName(surveyResponse.getRespondentFirstName())
                .respondentLastName(surveyResponse.getRespondentLastName())
                .respondentEmail(surveyResponse.getRespondentEmail())
                .submittedAt(surveyResponse.getSubmittedAt())
                .answers(toAnswerResponseDtos(surveyResponse.getAnswers()))
                .build();
    }

    public static List<SubmittedSurveyResponseDto> toSubmittedSurveyResponseDtos(
            List<SurveyResponse> surveyResponses
    ) {
        if (surveyResponses == null || surveyResponses.isEmpty()) {
            return Collections.emptyList();
        }

        return surveyResponses.stream()
                .filter(Objects::nonNull)
                .map(ResponseMapper::toSubmittedSurveyResponseDto)
                .toList();
    }

    public static AnswerResponseDto toAnswerResponseDto(Answer answer) {
        return AnswerResponseDto.builder()
                .id(answer.getId())
                .questionId(answer.getQuestion().getId())
                .questionText(answer.getQuestion().getQuestionText())
                .answerValue(answer.getAnswerValue())
                .build();
    }

    public static List<AnswerResponseDto> toAnswerResponseDtos(List<Answer> answers) {
        if (answers == null || answers.isEmpty()) {
            return Collections.emptyList();
        }

        return answers.stream()
                .filter(Objects::nonNull)
                .map(ResponseMapper::toAnswerResponseDto)
                .toList();
    }
}