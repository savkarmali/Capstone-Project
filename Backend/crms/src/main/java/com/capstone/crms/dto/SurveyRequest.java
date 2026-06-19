package com.capstone.crms.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

public record SurveyRequest(
        @NotBlank String title,
        String description,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        @Size(min = 1, max = 6) List<@Valid QuestionItem> questions) {
    public record QuestionItem(@NotBlank String text, @NotBlank String type, boolean required, List<String> options) {
    }
}
