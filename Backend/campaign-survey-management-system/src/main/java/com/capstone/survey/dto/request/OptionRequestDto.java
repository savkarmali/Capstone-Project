package com.capstone.survey.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionRequestDto {

    @NotBlank(message = "Option label is mandatory")
    private String optionLabel;

    @NotBlank(message = "Option value is mandatory")
    private String optionValue;

    @NotNull(message = "Display order is mandatory")
    @Positive(message = "Display order must be greater than zero")
    private Integer displayOrder;
}