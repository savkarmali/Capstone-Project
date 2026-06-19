package com.capstone.survey.service;

import java.time.LocalDate;

public interface ExportService {

    byte[] exportSurveyResponsesToExcel(
            Long surveyId,
            LocalDate startDate,
            LocalDate endDate
    );

    byte[] exportSurveyResponsesToCsv(
            Long surveyId,
            LocalDate startDate,
            LocalDate endDate
    );

    byte[] exportSurveyResponsesToPdf(
            Long surveyId,
            LocalDate startDate,
            LocalDate endDate
    );
}