package com.capstone.survey.validation;

import com.capstone.survey.dto.request.QuestionRequestDto;
import com.capstone.survey.dto.request.SurveyCreateRequestDto;
import com.capstone.survey.dto.request.SurveyUpdateRequestDto;
import com.capstone.survey.entity.Survey;

public interface SurveyValidator {

    void validateCreateSurvey(SurveyCreateRequestDto surveyCreateRequestDto);

    void validateUpdateSurvey(
            Survey survey,
            SurveyUpdateRequestDto surveyUpdateRequestDto
    );

    void validateSurveyEditable(Survey survey);

    void validateQuestionRequest(QuestionRequestDto questionRequestDto);

    void validateSurveyPublishable(Survey survey);
}