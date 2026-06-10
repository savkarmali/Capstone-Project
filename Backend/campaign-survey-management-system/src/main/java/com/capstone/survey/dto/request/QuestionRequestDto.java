package com.capstone.survey.dto.request;

import com.capstone.survey.enums.DataType;
import com.capstone.survey.enums.QuestionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequestDto {

    @NotBlank(message = "Question text is mandatory")
    private String questionText;

    @NotNull(message = "Question type is mandatory")
    private QuestionType questionType;

    @NotNull(message = "Data type is mandatory")
    private DataType dataType;

    @NotNull(message = "Required flag is mandatory")
    private Boolean required;

    @NotNull(message = "Display order is mandatory")
    @Positive(message = "Display order must be greater than zero")
    private Integer displayOrder;

    private Integer minValue;

    private Integer maxValue;

    @Valid
    private List<OptionRequestDto> options = new ArrayList<>();
}