package com.capstone.crms.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ResponseRequest(@NotBlank String name, @NotBlank String email, @NotEmpty List<@Valid AnswerItem> answers) {
    public record AnswerItem(@NotNull Long questionId, @NotBlank String answerText) {}
}
