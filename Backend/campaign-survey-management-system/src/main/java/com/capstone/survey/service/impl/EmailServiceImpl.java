package com.capstone.survey.service.impl;

import com.capstone.survey.entity.EmailLog;
import com.capstone.survey.enums.EmailStatus;
import com.capstone.survey.repository.EmailLogRepository;
import com.capstone.survey.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailLogRepository emailLogRepository;

    public EmailServiceImpl(
            JavaMailSender javaMailSender,
            EmailLogRepository emailLogRepository
    ) {
        this.javaMailSender = javaMailSender;
        this.emailLogRepository = emailLogRepository;
    }

    @Override
    @Transactional
    public void sendSurveySubmissionConfirmation(
            String recipientEmail,
            String respondentFirstName,
            String surveyTitle
    ) {
        String subject = "Survey Submission Confirmation";
        String body = "Hello " + respondentFirstName + ",\n\n"+
        "Thank you. Your response for survey \""+
        surveyTitle+
        "\" has been submitted successfully.\n\n"+
        "Regards,\nSurvey Management Team";

        sendEmail(recipientEmail, subject, body);
    }

    @Override
    @Transactional
    public void sendAdminResponseNotification(
            String adminEmail,
            String surveyTitle,
            String respondentEmail
    ) {
        String subject = "New Survey Response Received";
        String body = "A new response has been submitted for survey \""+
        surveyTitle+
        "\" by "+
        respondentEmail+
        ".";

        sendEmail(adminEmail, subject, body);
    }

    private void sendEmail(
            String recipient,
            String subject,
            String body
    ) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(recipient);
            message.setSubject(subject);
            message.setText(body);

            javaMailSender.send(message);

            saveEmailLog(recipient, subject, body, EmailStatus.SENT, null);
        } catch (Exception exception) {
            saveEmailLog(recipient, subject, body, EmailStatus.FAILED, exception.getMessage());
        }
    }

    private void saveEmailLog(
            String recipient,
            String subject,
            String body,
            EmailStatus status,
            String errorMessage
    ) {
        EmailLog emailLog = EmailLog.builder()
                .recipient(recipient)
                .subject(subject)
                .body(body)
                .status(status)
                .errorMessage(errorMessage)
                .build();

        emailLogRepository.save(emailLog);
    }
}