package com.capstone.survey.validation.impl;

import com.capstone.survey.dto.request.OptionRequestDto;
import com.capstone.survey.dto.request.QuestionRequestDto;
import com.capstone.survey.dto.request.SurveyCreateRequestDto;
import com.capstone.survey.dto.request.SurveyUpdateRequestDto;
import com.capstone.survey.entity.Survey;
import com.capstone.survey.enums.DataType;
import com.capstone.survey.enums.QuestionType;
import com.capstone.survey.enums.SurveyStatus;
import com.capstone.survey.exception.BadRequestException;
import com.capstone.survey.util.ApiConstants;
import com.capstone.survey.util.DateTimeUtil;
import com.capstone.survey.validation.SurveyValidator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class SurveyValidatorImpl implements SurveyValidator {

    @Override
    public void validateCreateSurvey(SurveyCreateRequestDto surveyCreateRequestDto) {
        validateDateRange(
                surveyCreateRequestDto.getStartDate(),
                surveyCreateRequestDto.getEndDate()
        );

        if (surveyCreateRequestDto.getQuestions() != null
                && surveyCreateRequestDto.getQuestions().size() > ApiConstants.MAX_QUESTIONS_PER_SURVEY) {
            throw new BadRequestException("Survey can have maximum 6 questions");
        }

        validateQuestionDisplayOrders(surveyCreateRequestDto.getQuestions());

        if (surveyCreateRequestDto.getQuestions() != null) {
            surveyCreateRequestDto.getQuestions()
                    .forEach(this::validateQuestionRequest);
        }
    }

    @Override
    public void validateUpdateSurvey(
            Survey survey,
            SurveyUpdateRequestDto surveyUpdateRequestDto
    ) {
        validateSurveyEditable(survey);

        validateDateRange(
                surveyUpdateRequestDto.getStartDate(),
                surveyUpdateRequestDto.getEndDate()
        );
    }

    @Override
    public void validateSurveyEditable(Survey survey) {
        if (SurveyStatus.DELETED.equals(survey.getStatus())) {
            throw new BadRequestException("Deleted survey cannot be modified");
        }

        if (!survey.isEditable()) {
            throw new BadRequestException("Published or closed survey cannot be edited");
        }
    }

    @Override
    public void validateQuestionRequest(QuestionRequestDto questionRequestDto) {
        if (questionRequestDto.getQuestionType() == QuestionType.RADIO
                || questionRequestDto.getQuestionType() == QuestionType.CHECKBOX) {
            validateOptions(questionRequestDto.getOptions());
        }

        if (questionRequestDto.getDataType() == DataType.NUMBER_RANGE) {
            validateNumberRange(
                    questionRequestDto.getMinValue(),
                    questionRequestDto.getMaxValue()
            );
        }
    }

    @Override
    public void validateSurveyPublishable(Survey survey) {
        validateSurveyEditable(survey);

        if (survey.getQuestions() == null || survey.getQuestions().isEmpty()) {
            throw new BadRequestException("Survey must have at least one question before publishing");
        }

        if (survey.getQuestions().size() > ApiConstants.MAX_QUESTIONS_PER_SURVEY) {
            throw new BadRequestException("Survey can have maximum 6 questions");
        }
    }

    private void validateDateRange(
            java.time.LocalDate startDate,
            java.time.LocalDate endDate
    ) {
        if (DateTimeUtil.isEndDateBeforeStartDate(startDate, endDate)) {
            throw new BadRequestException("Survey end date cannot be before start date");
        }
    }

    private void validateOptions(List<OptionRequestDto> options) {
        if (options == null || options.isEmpty()) {
            throw new BadRequestException("Radio and checkbox questions must have options");
        }

        Set<Integer> displayOrders = new HashSet<>();

        for (OptionRequestDto option : options) {
            if (!displayOrders.add(option.getDisplayOrder())) {
                throw new BadRequestException("Duplicate option display order is not allowed");
            }
        }
    }

    private void validateNumberRange(Integer minValue, Integer maxValue) {
        if (minValue == null || maxValue == null) {
            throw new BadRequestException("Minimum and maximum values are mandatory for number range");
        }

        if (maxValue < minValue) {
            throw new BadRequestException("Maximum value cannot be less than minimum value");
        }
    }

    private void validateQuestionDisplayOrders(List<QuestionRequestDto> questions) {
        if (questions == null || questions.isEmpty()) {
            return;
        }

        Set<Integer> displayOrders = new HashSet<>();

        for (QuestionRequestDto question : questions) {
            if (!displayOrders.add(question.getDisplayOrder())) {
                throw new BadRequestException("Duplicate question display order is not allowed");
            }
        }
    }
}