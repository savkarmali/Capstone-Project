package com.capstone.survey.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerRequestDto {

    @NotNull(message = "Question ID is mandatory")
    private Long questionId;

    private String answerValue;
}