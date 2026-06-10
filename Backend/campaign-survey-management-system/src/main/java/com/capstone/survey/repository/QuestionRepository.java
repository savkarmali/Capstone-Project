package com.capstone.survey.repository;

import com.capstone.survey.entity.Question;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findBySurveyIdOrderByDisplayOrderAsc(Long surveyId);

    long countBySurveyId(Long surveyId);

    boolean existsBySurveyIdAndDisplayOrder(Long surveyId, Integer displayOrder);
}