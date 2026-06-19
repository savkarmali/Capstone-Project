package com.capstone.crms.repository;

import com.capstone.crms.entity.Survey;
import com.capstone.crms.entity.SurveyResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<SurveyResponse, Long> {

    boolean existsBySurveyAndEmailIgnoreCase(Survey survey, String email);

    @Query("select r from SurveyResponse r where (:surveyId is null or r.survey.id = :surveyId) " +
            "and (:q is null or lower(r.name) like lower(concat('%', :q, '%')) or lower(r.email) like lower(concat('%', :q, '%'))) " +
            "and (:from is null or r.submittedAt >= :from) and (:to is null or r.submittedAt <= :to)")
    List<SurveyResponse> search(@Param("surveyId") Long surveyId, @Param("q") String q,
                                @Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("select cast(r.submittedAt as date), count(r) from SurveyResponse r group by cast(r.submittedAt as date) order by cast(r.submittedAt as date)")
    List<Object[]> trend();
}
