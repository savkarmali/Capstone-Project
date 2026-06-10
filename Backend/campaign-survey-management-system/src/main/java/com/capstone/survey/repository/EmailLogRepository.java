package com.capstone.survey.repository;

import com.capstone.survey.entity.EmailLog;
import com.capstone.survey.enums.EmailStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {

    List<EmailLog> findByRecipientOrderBySentAtDesc(String recipient);

    List<EmailLog> findByStatusOrderBySentAtDesc(EmailStatus status);
}