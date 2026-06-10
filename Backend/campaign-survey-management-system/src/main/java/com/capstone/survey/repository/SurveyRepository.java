package com.capstone.survey.repository;

import com.capstone.survey.entity.Survey;
import com.capstone.survey.enums.SurveyStatus;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Optional<Survey> findByPublicToken(String publicToken);

    Optional<Survey> findByPublicTokenAndStatus(String publicToken, SurveyStatus status);

    Page<Survey> findByStatusNot(SurveyStatus status, Pageable pageable);

    Page<Survey> findByStatus(SurveyStatus status, Pageable pageable);

    long countByStatus(SurveyStatus status);

    long countByStatusNot(SurveyStatus status);

    Page<Survey> findByStartDateGreaterThanEqualAndEndDateLessThanEqual(
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );
}