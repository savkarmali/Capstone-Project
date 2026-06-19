package com.capstone.survey.service.impl;

import com.capstone.survey.dto.request.AnswerRequestDto;
import com.capstone.survey.dto.request.SubmitSurveyRequestDto;
import com.capstone.survey.dto.response.SubmittedSurveyResponseDto;
import com.capstone.survey.entity.Answer;
import com.capstone.survey.entity.Question;
import com.capstone.survey.entity.Survey;
import com.capstone.survey.entity.SurveyResponse;
import com.capstone.survey.enums.SurveyStatus;
import com.capstone.survey.exception.BadRequestException;
import com.capstone.survey.exception.DuplicateResourceException;
import com.capstone.survey.exception.ResourceNotFoundException;
import com.capstone.survey.mapper.ResponseMapper;
import com.capstone.survey.repository.QuestionRepository;
import com.capstone.survey.repository.SurveyRepository;
import com.capstone.survey.repository.SurveyResponseRepository;
import com.capstone.survey.service.EmailService;
import com.capstone.survey.service.ResponseService;
import com.capstone.survey.util.DateTimeUtil;
import com.capstone.survey.validation.AnswerValidator;
import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResponseServiceImpl implements ResponseService {

    private final SurveyRepository surveyRepository;
    private final SurveyResponseRepository surveyResponseRepository;
    private final QuestionRepository questionRepository;
    private final AnswerValidator answerValidator;
    private final EmailService emailService;

    public ResponseServiceImpl(
            SurveyRepository surveyRepository,
            SurveyResponseRepository surveyResponseRepository,
            QuestionRepository questionRepository,
            AnswerValidator answerValidator,
            EmailService emailService
    ) {
        this.surveyRepository = surveyRepository;
        this.surveyResponseRepository = surveyResponseRepository;
        this.questionRepository = questionRepository;
        this.answerValidator = answerValidator;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public SubmittedSurveyResponseDto submitSurvey(
            String publicToken,
            SubmitSurveyRequestDto submitSurveyRequestDto
    ) {
        Survey survey = surveyRepository.findByPublicTokenAndStatus(
                        publicToken,
                        SurveyStatus.PUBLISHED
                )
                .orElseThrow(() -> new ResourceNotFoundException("Published survey not found"));

        if (!survey.isWithinValidityPeriod(DateTimeUtil.today())) {
            throw new BadRequestException("Survey is not available currently");
        }

        if (surveyResponseRepository.existsBySurveyIdAndRespondentEmail(
                survey.getId(),
                submitSurveyRequestDto.getRespondentEmail()
        )) {
            throw new DuplicateResourceException("You have already submitted this survey");
        }

        Map<Long, Question> questionMap = questionRepository
                .findBySurveyIdOrderByDisplayOrderAsc(survey.getId())
                .stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));

        Map<Long, AnswerRequestDto> answerMap = submitSurveyRequestDto.getAnswers()
                .stream()
                .collect(Collectors.toMap(AnswerRequestDto::getQuestionId, Function.identity()));

        validateRequiredQuestions(questionMap, answerMap);
        validateSubmittedAnswers(questionMap, answerMap);

        SurveyResponse surveyResponse = SurveyResponse.builder()
                .survey(survey)
                .respondentFirstName(submitSurveyRequestDto.getRespondentFirstName())
                .respondentLastName(submitSurveyRequestDto.getRespondentLastName())
                .respondentEmail(submitSurveyRequestDto.getRespondentEmail())
                .build();

        for (AnswerRequestDto answerRequestDto : submitSurveyRequestDto.getAnswers()) {
            Question question = questionMap.get(answerRequestDto.getQuestionId());

            Answer answer = Answer.builder()
                    .surveyResponse(surveyResponse)
                    .question(question)
                    .answerValue(answerRequestDto.getAnswerValue())
                    .build();

            surveyResponse.getAnswers().add(answer);
        }

        SurveyResponse savedResponse = surveyResponseRepository.save(surveyResponse);

        emailService.sendSurveySubmissionConfirmation(
                savedResponse.getRespondentEmail(),
                savedResponse.getRespondentFirstName(),
                survey.getTitle()
        );

        return ResponseMapper.toSubmittedSurveyResponseDto(savedResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubmittedSurveyResponseDto> getResponsesBySurvey(
            Long surveyId,
            Pageable pageable
    ) {
        ensureSurveyExists(surveyId);

        return surveyResponseRepository.findBySurveyId(surveyId, pageable)
                .map(ResponseMapper::toSubmittedSurveyResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubmittedSurveyResponseDto> getResponsesBySurveyAndDateRange(
            Long surveyId,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    ) {
        ensureSurveyExists(surveyId);

        if (DateTimeUtil.isEndDateBeforeStartDate(startDate, endDate)) {
            throw new BadRequestException("End date cannot be before start date");
        }

        return surveyResponseRepository.findBySurveyIdAndSubmittedAtBetween(
                        surveyId,
                        DateTimeUtil.startOfDay(startDate),
                        DateTimeUtil.endOfDay(endDate),
                        pageable
                )
                .map(ResponseMapper::toSubmittedSurveyResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public SubmittedSurveyResponseDto getResponseById(Long responseId) {
        SurveyResponse surveyResponse = surveyResponseRepository.findById(responseId)
                .orElseThrow(() -> new ResourceNotFoundException("Response not found with id: " + responseId));

        return ResponseMapper.toSubmittedSurveyResponseDto(surveyResponse);
    }

    private void validateRequiredQuestions(
            Map<Long, Question> questionMap,
            Map<Long, AnswerRequestDto> answerMap
    ) {
        for (Question question : questionMap.values()) {
            if (Boolean.TRUE.equals(question.getRequired())
                    && !answerMap.containsKey(question.getId())) {
                throw new BadRequestException("Mandatory question is not answered: " +
                        question.getQuestionText());
            }
        }
    }

    private void validateSubmittedAnswers(
            Map<Long, Question> questionMap,
            Map<Long, AnswerRequestDto> answerMap
    ) {
        for (AnswerRequestDto answerRequestDto : answerMap.values()) {
            Question question = questionMap.get(answerRequestDto.getQuestionId());

            if (question == null) {
                throw new BadRequestException("Invalid question submitted: "+
                        answerRequestDto.getQuestionId());
            }

            answerValidator.validateAnswer(question, answerRequestDto.getAnswerValue());
        }
    }

    private void ensureSurveyExists(Long surveyId) {
        surveyRepository.findById(surveyId)
                .filter(survey -> !SurveyStatus.DELETED.equals(survey.getStatus()))
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id: " + surveyId));
    }
}