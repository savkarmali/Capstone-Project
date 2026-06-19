package com.capstone.crms.service;

import com.capstone.crms.dto.SurveyRequest;
import com.capstone.crms.entity.*;
import com.capstone.crms.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public List<Survey> all() {
        return surveyRepository.findAll();
    }

    public Survey byId(Long id) {
        return surveyRepository.findById(id).orElseThrow(() -> new RuntimeException("Survey not found"));
    }

    public Survey byToken(String token) {
        Survey survey = surveyRepository.findByPublicToken(token).orElseThrow(() -> new RuntimeException("Survey not found"));
        LocalDate today = LocalDate.now();
        if (!"PUBLISHED".equals(survey.getStatus()) || today.isBefore(survey.getStartDate()) || today.isAfter(survey.getEndDate())) {
            throw new RuntimeException("Survey is not active");
        }
        return survey;
    }

    @Transactional
    public Survey create(SurveyRequest request) {
        validate(request);
        Survey survey = Survey.builder()
                .title(request.title())
                .description(request.description())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .status("DRAFT")
                .createdAt(LocalDateTime.now())
                .publicToken(UUID.randomUUID().toString())
                .questions(new ArrayList<>())
                .build();
        addQuestions(survey, request.questions());
        return surveyRepository.save(survey);
    }

    @Transactional
    public Survey update(Long id, SurveyRequest request) {
        validate(request);
        Survey survey = byId(id);
        if (!"DRAFT".equals(survey.getStatus())) throw new RuntimeException("Published survey cannot be edited");
        survey.setTitle(request.title());
        survey.setDescription(request.description());
        survey.setStartDate(request.startDate());
        survey.setEndDate(request.endDate());
        survey.getQuestions().clear();
        addQuestions(survey, request.questions());
        return survey;
    }

    @Transactional
    public Survey publish(Long id) {
        Survey survey = byId(id);
        survey.setStatus("PUBLISHED");
        return survey;
    }

    public void delete(Long id) {
        surveyRepository.delete(byId(id));
    }

    private void validate(SurveyRequest request) {
        if (request.endDate().isBefore(request.startDate())) throw new RuntimeException("End date must be after start date");
        if (request.questions() == null || request.questions().isEmpty() || request.questions().size() > 6) {
            throw new RuntimeException("Survey must have 1 to 6 questions");
        }
    }

    private void addQuestions(Survey survey, List<SurveyRequest.QuestionItem> items) {
        int pos = 1;
        for (SurveyRequest.QuestionItem item : items) {
            Question question = Question.builder()
                    .survey(survey)
                    .text(item.text())
                    .type(item.type().toUpperCase())
                    .required(item.required())
                    .position(pos++)
                    .options(new ArrayList<>())
                    .build();
            if (item.options() != null) {
                for (String option : item.options()) {
                    question.getOptions().add(QuestionOption.builder().question(question).labelValue(option).build());
                }
            }
            survey.getQuestions().add(question);
        }
    }
}
