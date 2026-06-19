package com.capstone.survey.service.impl;

import com.capstone.survey.dto.response.AnalyticsDashboardResponseDto;
import com.capstone.survey.dto.response.QuestionAnalyticsResponseDto;
import com.capstone.survey.dto.response.SurveyResponseCountDto;
import com.capstone.survey.entity.Answer;
import com.capstone.survey.entity.Question;
import com.capstone.survey.entity.Survey;
import com.capstone.survey.enums.QuestionType;
import com.capstone.survey.enums.SurveyStatus;
import com.capstone.survey.exception.ResourceNotFoundException;
import com.capstone.survey.repository.AnswerRepository;
import com.capstone.survey.repository.QuestionRepository;
import com.capstone.survey.repository.SurveyRepository;
import com.capstone.survey.repository.SurveyResponseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class AnalyticsServiceImpl implements com.capstone.survey.service.AnalyticsService {

    private final SurveyRepository surveyRepository;
    private final SurveyResponseRepository surveyResponseRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ObjectMapper objectMapper;

    public AnalyticsServiceImpl(
            SurveyRepository surveyRepository,
            SurveyResponseRepository surveyResponseRepository,
            QuestionRepository questionRepository,
            AnswerRepository answerRepository,
            ObjectMapper objectMapper
    ) {
        this.surveyRepository = surveyRepository;
        this.surveyResponseRepository = surveyResponseRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public AnalyticsDashboardResponseDto getDashboardAnalytics() {
        List<SurveyResponseCountDto> responsesBySurvey = surveyRepository
                .findByStatusNot(SurveyStatus.DELETED, Pageable.unpaged())
                .stream()
                .map(survey -> SurveyResponseCountDto.builder()
                        .surveyId(survey.getId())
                        .surveyTitle(survey.getTitle())
                        .responseCount(surveyResponseRepository.countBySurveyId(survey.getId()))
                        .build())
                .toList();

        long totalResponses = responsesBySurvey.stream()
                .mapToLong(SurveyResponseCountDto::getResponseCount)
                .sum();

        return AnalyticsDashboardResponseDto.builder()
                .totalSurveys(surveyRepository.countByStatusNot(SurveyStatus.DELETED))
                .draftSurveys(surveyRepository.countByStatus(SurveyStatus.DRAFT))
                .publishedSurveys(surveyRepository.countByStatus(SurveyStatus.PUBLISHED))
                .closedSurveys(surveyRepository.countByStatus(SurveyStatus.CLOSED))
                .totalResponses(totalResponses)
                .responsesBySurvey(responsesBySurvey)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionAnalyticsResponseDto> getSurveyQuestionAnalytics(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .filter(existingSurvey -> !SurveyStatus.DELETED.equals(existingSurvey.getStatus()))
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id: " + surveyId));

        return questionRepository.findBySurveyIdOrderByDisplayOrderAsc(survey.getId())
                .stream()
                .map(this::buildQuestionAnalytics)
                .toList();
    }

    private QuestionAnalyticsResponseDto buildQuestionAnalytics(Question question) {
        List<Answer> answers = answerRepository.findByQuestionId(question.getId());

        Map<String, Long> distribution = answers.stream()
                .filter(answer -> StringUtils.hasText(answer.getAnswerValue()))
                .flatMap(answer -> splitAnswerValues(question, answer.getAnswerValue()).stream())
                .collect(Collectors.groupingBy(
                        value -> value,
                        LinkedHashMap::new,
                        Collectors.counting()
                ));

        return QuestionAnalyticsResponseDto.builder()
                .questionId(question.getId())
                .questionText(question.getQuestionText())
                .questionType(question.getQuestionType().name())
                .answerDistribution(distribution)
                .build();
    }

    private List<String> splitAnswerValues(
            Question question,
            String answerValue
    ) {
        if (QuestionType.CHECKBOX.equals(question.getQuestionType())) {
            return parseCheckboxAnswer(answerValue);
        }

        return List.of(answerValue);
    }

    private List<String> parseCheckboxAnswer(String answerValue) {
        try {
            return objectMapper.readValue(answerValue, new TypeReference<List<String>>() {
            });
        } catch (Exception exception) {
            return List.of(answerValue.split(","))
                    .stream()
                    .map(String::trim)
                    .filter(StringUtils::hasText)
                    .toList();
        }
    }
}