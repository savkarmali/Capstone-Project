package com.capstone.survey.validation;

import com.capstone.survey.entity.Question;

public interface AnswerValidator {

    void validateAnswer(
            Question question,
            String answerValue
    );
}