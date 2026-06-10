package com.capstone.survey.dto.response;

import com.capstone.survey.enums.DataType;
import com.capstone.survey.enums.QuestionType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestionResponseDto {

    private final Long id;

    private final String questionText;

    private final QuestionType questionType;

    private final DataType dataType;

    private final Boolean required;

    private final Integer displayOrder;

    private final Integer minValue;

    private final Integer maxValue;

    private final List<OptionResponseDto> options;
}