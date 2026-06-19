package com.capstone.survey.mapper;

import com.capstone.survey.dto.response.OptionResponseDto;
import com.capstone.survey.dto.response.QuestionResponseDto;
import com.capstone.survey.dto.response.SurveyResponseDto;
import com.capstone.survey.entity.Question;
import com.capstone.survey.entity.QuestionOption;
import com.capstone.survey.entity.Survey;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class SurveyMapper {

    private SurveyMapper() {
    }

    public static SurveyResponseDto toSurveyResponseDto(
            Survey survey,
            long responseCount
    ) {
        return SurveyResponseDto.builder()
                .id(survey.getId())
                .title(survey.getTitle())
                .description(survey.getDescription())
                .status(survey.getStatus())
                .publicToken(survey.getPublicToken())
                .surveyUrl(survey.getSurveyUrl())
                .startDate(survey.getStartDate())
                .endDate(survey.getEndDate())
                .createdById(survey.getCreatedBy().getId())
                .createdByName(buildCreatedByName(survey))
                .createdAt(survey.getCreatedAt())
                .publishedAt(survey.getPublishedAt())
                .closedAt(survey.getClosedAt())
                .updatedAt(survey.getUpdatedAt())
                .responseCount(responseCount)
                .questions(toQuestionResponseDtos(survey.getQuestions()))
                .build();
    }

    public static List<QuestionResponseDto> toQuestionResponseDtos(
            List<Question> questions
    ) {
        if (questions == null || questions.isEmpty()) {
            return Collections.emptyList();
        }

        return questions.stream()
                .filter(Objects::nonNull)
                .map(SurveyMapper::toQuestionResponseDto)
                .toList();
    }

    public static QuestionResponseDto toQuestionResponseDto(Question question) {
        return QuestionResponseDto.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .questionType(question.getQuestionType())
                .dataType(question.getDataType())
                .required(question.getRequired())
                .displayOrder(question.getDisplayOrder())
                .minValue(question.getMinValue())
                .maxValue(question.getMaxValue())
                .options(toOptionResponseDtos(question.getOptions()))
                .build();
    }

    public static List<OptionResponseDto> toOptionResponseDtos(
            List<QuestionOption> options
    ) {
        if (options == null || options.isEmpty()) {
            return Collections.emptyList();
        }

        return options.stream()
                .filter(Objects::nonNull)
                .map(SurveyMapper::toOptionResponseDto)
                .toList();
    }

    public static OptionResponseDto toOptionResponseDto(QuestionOption option) {
        return OptionResponseDto.builder()
                .id(option.getId())
                .optionLabel(option.getOptionLabel())
                .optionValue(option.getOptionValue())
                .displayOrder(option.getDisplayOrder())
                .build();
    }

    private static String buildCreatedByName(Survey survey) {
        String firstName = survey.getCreatedBy().getFirstName();
        String lastName = survey.getCreatedBy().getLastName();

        if (lastName == null || lastName.isBlank()) {
            return firstName;
        }

        return firstName + " " + lastName;
    }
}