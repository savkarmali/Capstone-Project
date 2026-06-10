package com.capstone.survey.repository;

import com.capstone.survey.entity.SurveyResponse;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {

    Page<SurveyResponse> findBySurveyId(Long surveyId, Pageable pageable);

    boolean existsBySurveyIdAndRespondentEmail(Long surveyId, String respondentEmail);

    long countBySurveyId(Long surveyId);

    long countBySubmittedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    Page<SurveyResponse> findBySurveyIdAndSubmittedAtBetween(
            Long surveyId,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            Pageable pageable
    );
}