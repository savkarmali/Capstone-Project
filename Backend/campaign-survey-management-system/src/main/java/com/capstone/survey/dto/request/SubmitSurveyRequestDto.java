package com.capstone.survey.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitSurveyRequestDto {

    @NotBlank(message = "First name is mandatory")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    private String respondentFirstName;

    @Size(max = 100, message = "Last name must not exceed 100 characters")
    private String respondentLastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email must be valid")
    @Size(max = 150, message = "Email must not exceed 150 characters")
    private String respondentEmail;

    @Valid
    @Size(min = 1, message = "At least one answer is mandatory")
    private List<AnswerRequestDto> answers = new ArrayList<>();
}