package com.capstone.survey.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OptionResponseDto {

    private final Long id;

    private final String optionLabel;

    private final String optionValue;

    private final Integer displayOrder;
}