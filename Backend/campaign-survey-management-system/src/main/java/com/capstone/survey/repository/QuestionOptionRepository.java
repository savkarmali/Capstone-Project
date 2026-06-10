package com.capstone.survey.repository;

import com.capstone.survey.entity.QuestionOption;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long> {

    List<QuestionOption> findByQuestionIdOrderByDisplayOrderAsc(Long questionId);

    void deleteByQuestionId(Long questionId);
}