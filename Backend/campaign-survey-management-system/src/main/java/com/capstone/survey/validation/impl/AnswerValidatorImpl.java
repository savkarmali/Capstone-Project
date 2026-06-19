package com.capstone.survey.validation.impl;

import com.capstone.survey.entity.Question;
import com.capstone.survey.enums.DataType;
import com.capstone.survey.enums.QuestionType;
import com.capstone.survey.exception.BadRequestException;
import com.capstone.survey.validation.AnswerValidator;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AnswerValidatorImpl implements AnswerValidator {

    private static final String ALPHABETS_ONLY_REGEX = "^[A-Za-z ]+$";
    private static final String ALPHANUMERIC_REGEX = "^[A-Za-z0-9 ]+$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Override
    public void validateAnswer(
            Question question,
            String answerValue
    ) {
        if (Boolean.TRUE.equals(question.getRequired())
                && !StringUtils.hasText(answerValue)) {
            throw new BadRequestException("Answer is mandatory for question: "
                    + question.getQuestionText());
        }

        if (!StringUtils.hasText(answerValue)) {
            return;
        }

        validateByQuestionType(question, answerValue);
        validateByDataType(question, answerValue);
    }

    private void validateByQuestionType(
            Question question,
            String answerValue
    ) {
        if (QuestionType.RADIO.equals(question.getQuestionType())) {
            validateSingleOption(question, answerValue);
        }

        if (QuestionType.CHECKBOX.equals(question.getQuestionType())) {
            validateMultipleOptions(question, answerValue);
        }
    }

    private void validateByDataType(
            Question question,
            String answerValue
    ) {
        DataType dataType = question.getDataType();

        if (DataType.ALPHABETS_ONLY.equals(dataType)
                && !answerValue.matches(ALPHABETS_ONLY_REGEX)) {
            throw new BadRequestException("Answer must contain alphabets only for question: "
                    + question.getQuestionText());
        }

        if (DataType.ALPHANUMERIC.equals(dataType)
                && !answerValue.matches(ALPHANUMERIC_REGEX)) {
            throw new BadRequestException("Answer must be alphanumeric for question: "
                    + question.getQuestionText());
        }

        if (DataType.EMAIL.equals(dataType)
                && !answerValue.matches(EMAIL_REGEX)) {
            throw new BadRequestException("Answer must be a valid email for question: "
                    + question.getQuestionText());
        }

        if (DataType.NUMBER.equals(dataType)
                || DataType.NUMBER_RANGE.equals(dataType)) {
            validateNumber(question, answerValue);
        }

        if (DataType.DATE.equals(dataType)) {
            validateDate(question, answerValue);
        }
    }

    private void validateNumber(
            Question question,
            String answerValue
    ) {
        try {
            int number = Integer.parseInt(answerValue);

            if (question.hasNumberRangeValidation()
                    && (number < question.getMinValue() || number > question.getMaxValue())) {
                throw new BadRequestException("Answer must be between "
                        + question.getMinValue()
                        + " and "
                        + question.getMaxValue()
                        + " for question: "
                        + question.getQuestionText());
            }
        } catch (NumberFormatException exception) {
            throw new BadRequestException("Answer must be a number for question: "
                    + question.getQuestionText());
        }
    }

    private void validateDate(
            Question question,
            String answerValue
    ) {
        try {
            LocalDate.parse(answerValue);
        } catch (DateTimeParseException exception) {
            throw new BadRequestException("Answer must be a valid date yyyy-MM-dd for question: "
                    + question.getQuestionText());
        }
    }

    private void validateSingleOption(
            Question question,
            String answerValue
    ) {
        Set<String> validOptions = getValidOptionValues(question);

        if (!validOptions.contains(answerValue)) {
            throw new BadRequestException("Invalid option selected for question: "
                    + question.getQuestionText());
        }
    }

    private void validateMultipleOptions(
            Question question,
            String answerValue
    ) {
        Set<String> validOptions = getValidOptionValues(question);

        Set<String> selectedValues = Arrays.stream(answerValue.split(","))
                .map(String::trim)
                .filter(StringUtils::hasText)
                .collect(Collectors.toSet());

        if (selectedValues.isEmpty()) {
            throw new BadRequestException("At least one option must be selected for question: "
                    + question.getQuestionText());
        }

        if (!validOptions.containsAll(selectedValues)) {
            throw new BadRequestException("Invalid checkbox option selected for question: "
                    + question.getQuestionText());
        }
    }

    private Set<String> getValidOptionValues(Question question) {
        return question.getOptions()
                .stream()
                .map(option -> option.getOptionValue())
                .collect(Collectors.toSet());
    }
}