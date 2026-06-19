package com.capstone.survey.service;

public interface EmailService {

    void sendSurveySubmissionConfirmation(
            String recipientEmail,
            String respondentFirstName,
            String surveyTitle
    );

    void sendAdminResponseNotification(
            String adminEmail,
            String surveyTitle,
            String respondentEmail
    );
}