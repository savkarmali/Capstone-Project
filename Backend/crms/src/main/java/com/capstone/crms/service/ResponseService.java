package com.capstone.crms.service;

import com.capstone.crms.dto.ResponseRequest;
import com.capstone.crms.entity.*;
import com.capstone.crms.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ResponseService {
    private final ResponseRepository responseRepository;
    private final SurveyService surveyService;
    private final QuestionRepository questionRepository;

    @Transactional
    public SurveyResponse submit(String token, ResponseRequest request) {
        Survey survey = surveyService.byToken(token);
        if (responseRepository.existsBySurveyAndEmailIgnoreCase(survey, request.email())) {
            throw new RuntimeException("This email has already submitted the survey");
        }
        SurveyResponse response = SurveyResponse.builder()
                .survey(survey)
                .name(request.name())
                .email(request.email())
                .submittedAt(LocalDateTime.now())
                .answers(new ArrayList<>())
                .build();
        for (ResponseRequest.AnswerItem item : request.answers()) {
            Question question = questionRepository.findById(item.questionId()).orElseThrow(() -> new RuntimeException("Question not found"));
            response.getAnswers().add(ResponseAnswer.builder().response(response).question(question).answerText(item.answerText()).build());
        }
        return responseRepository.save(response);
    }

    public List<SurveyResponse> search(Long surveyId, String q, LocalDate from, LocalDate to) {
        LocalDateTime start = from == null ? null : from.atStartOfDay();
        LocalDateTime end = to == null ? null : to.atTime(23, 59, 59);
        return responseRepository.search(surveyId, q, start, end);
    }

    public String csv(Long surveyId, LocalDate from, LocalDate to) {
        StringBuilder sb = new StringBuilder("ResponseId,Survey,Name,Email,SubmittedAt,Question,Answer\n");
        for (SurveyResponse r : search(surveyId, null, from, to)) {
            for (ResponseAnswer a : r.getAnswers()) {
                sb.append(r.getId()).append(',')
                        .append(clean(r.getSurvey().getTitle())).append(',')
                        .append(clean(r.getName())).append(',')
                        .append(clean(r.getEmail())).append(',')
                        .append(r.getSubmittedAt()).append(',')
                        .append(clean(a.getQuestion().getText())).append(',')
                        .append(clean(a.getAnswerText())).append('\n');
            }
        }
        return sb.toString();
    }

    private String clean(String value) {
        return "\"" + (value == null ? "" : value.replace("\"", "\"\"")) + "\"";
    }
}
