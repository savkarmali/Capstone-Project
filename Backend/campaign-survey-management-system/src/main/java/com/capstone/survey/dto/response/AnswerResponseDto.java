package com.capstone.survey.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AnswerResponseDto {

    private final Long id;

    private final Long questionId;

    private final String questionText;

    private final String answerValue;
}