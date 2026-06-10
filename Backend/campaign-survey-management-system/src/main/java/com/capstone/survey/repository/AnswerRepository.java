package com.capstone.survey.repository;

import com.capstone.survey.entity.Answer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findBySurveyResponseId(Long surveyResponseId);

    List<Answer> findByQuestionId(Long questionId);
}