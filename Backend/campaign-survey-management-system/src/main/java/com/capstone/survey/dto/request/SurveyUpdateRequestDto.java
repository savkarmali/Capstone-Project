package com.capstone.survey.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SurveyUpdateRequestDto {

    @NotBlank(message = "Survey title is mandatory")
    @Size(max = 200, message = "Survey title must not exceed 200 characters")
    private String title;

    @NotBlank(message = "Survey description is mandatory")
    @Size(max = 1000, message = "Survey description must not exceed 1000 characters")
    private String description;

    @NotNull(message = "Survey start date is mandatory")
    @FutureOrPresent(message = "Survey start date cannot be in the past")
    private LocalDate startDate;

    @NotNull(message = "Survey end date is mandatory")
    @FutureOrPresent(message = "Survey end date cannot be in the past")
    private LocalDate endDate;
}