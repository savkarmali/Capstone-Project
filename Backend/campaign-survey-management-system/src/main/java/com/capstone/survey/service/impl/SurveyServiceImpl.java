package com.capstone.survey.service.impl;

import com.capstone.survey.dto.request.OptionRequestDto;
import com.capstone.survey.dto.request.QuestionRequestDto;
import com.capstone.survey.dto.request.SurveyCreateRequestDto;
import com.capstone.survey.dto.request.SurveyUpdateRequestDto;
import com.capstone.survey.dto.response.SurveyResponseDto;
import com.capstone.survey.entity.Question;
import com.capstone.survey.entity.QuestionOption;
import com.capstone.survey.entity.Survey;
import com.capstone.survey.entity.User;
import com.capstone.survey.enums.SurveyStatus;
import com.capstone.survey.exception.ResourceNotFoundException;
import com.capstone.survey.mapper.SurveyMapper;
import com.capstone.survey.repository.SurveyRepository;
import com.capstone.survey.repository.SurveyResponseRepository;
import com.capstone.survey.repository.UserRepository;
import com.capstone.survey.service.SurveyService;
import com.capstone.survey.util.DateTimeUtil;
import com.capstone.survey.util.TokenGenerator;
import com.capstone.survey.validation.SurveyValidator;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;
    private final SurveyResponseRepository surveyResponseRepository;
    private final UserRepository userRepository;
    private final SurveyValidator surveyValidator;
    private final String frontendBaseUrl;

    public SurveyServiceImpl(
            SurveyRepository surveyRepository,
            SurveyResponseRepository surveyResponseRepository,
            UserRepository userRepository,
            SurveyValidator surveyValidator,
            @Value("${app.frontend-base-url:http://localhost:4200}") String frontendBaseUrl
    ) {
        this.surveyRepository = surveyRepository;
        this.surveyResponseRepository = surveyResponseRepository;
        this.userRepository = userRepository;
        this.surveyValidator = surveyValidator;
        this.frontendBaseUrl = frontendBaseUrl;
    }

    @Override
    @Transactional
    public SurveyResponseDto createSurvey(
            SurveyCreateRequestDto surveyCreateRequestDto,
            String adminEmail
    ) {
        surveyValidator.validateCreateSurvey(surveyCreateRequestDto);

        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));

        Survey survey = Survey.builder()
                .title(surveyCreateRequestDto.getTitle())
                .description(surveyCreateRequestDto.getDescription())
                .startDate(surveyCreateRequestDto.getStartDate())
                .endDate(surveyCreateRequestDto.getEndDate())
                .status(SurveyStatus.DRAFT)
                .createdBy(admin)
                .build();

        addQuestionsToSurvey(survey, surveyCreateRequestDto.getQuestions());

        Survey savedSurvey = surveyRepository.save(survey);

        return SurveyMapper.toSurveyResponseDto(savedSurvey, 0);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SurveyResponseDto> getSurveys(
            SurveyStatus status,
            Pageable pageable
    ) {
        Page<Survey> surveys = status == null
                ? surveyRepository.findByStatusNot(SurveyStatus.DELETED, pageable)
                : surveyRepository.findByStatus(status, pageable);

        return surveys.map(survey -> SurveyMapper.toSurveyResponseDto(
                survey,
                surveyResponseRepository.countBySurveyId(survey.getId())
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public SurveyResponseDto getSurveyById(Long surveyId) {
        Survey survey = getSurveyOrThrow(surveyId);

        return SurveyMapper.toSurveyResponseDto(
                survey,
                surveyResponseRepository.countBySurveyId(survey.getId())
        );
    }

    @Override
    @Transactional
    public SurveyResponseDto updateSurvey(
            Long surveyId,
            SurveyUpdateRequestDto surveyUpdateRequestDto
    ) {
        Survey survey = getSurveyOrThrow(surveyId);

        surveyValidator.validateUpdateSurvey(survey, surveyUpdateRequestDto);

        survey.setTitle(surveyUpdateRequestDto.getTitle());
        survey.setDescription(surveyUpdateRequestDto.getDescription());
        survey.setStartDate(surveyUpdateRequestDto.getStartDate());
        survey.setEndDate(surveyUpdateRequestDto.getEndDate());

        Survey updatedSurvey = surveyRepository.save(survey);

        return SurveyMapper.toSurveyResponseDto(
                updatedSurvey,
                surveyResponseRepository.countBySurveyId(updatedSurvey.getId())
        );
    }

    @Override
    @Transactional
    public SurveyResponseDto publishSurvey(Long surveyId) {
        Survey survey = getSurveyOrThrow(surveyId);

        surveyValidator.validateSurveyPublishable(survey);

        String publicToken = TokenGenerator.generatePublicToken();

        survey.setPublicToken(publicToken);
        survey.setSurveyUrl(frontendBaseUrl + "/survey/public/" + publicToken);
        survey.setStatus(SurveyStatus.PUBLISHED);
        survey.setPublishedAt(DateTimeUtil.now());

        Survey publishedSurvey = surveyRepository.save(survey);

        return SurveyMapper.toSurveyResponseDto(
                publishedSurvey,
                surveyResponseRepository.countBySurveyId(publishedSurvey.getId())
        );
    }

    @Override
    @Transactional
    public SurveyResponseDto closeSurvey(Long surveyId) {
        Survey survey = getSurveyOrThrow(surveyId);

        survey.setStatus(SurveyStatus.CLOSED);
        survey.setClosedAt(DateTimeUtil.now());

        Survey closedSurvey = surveyRepository.save(survey);

        return SurveyMapper.toSurveyResponseDto(
                closedSurvey,
                surveyResponseRepository.countBySurveyId(closedSurvey.getId())
        );
    }

    @Override
    @Transactional
    public void deleteSurvey(Long surveyId) {
        Survey survey = getSurveyOrThrow(surveyId);

        survey.setStatus(SurveyStatus.DELETED);
        surveyRepository.save(survey);
    }

    @Override
    @Transactional(readOnly = true)
    public SurveyResponseDto getPublicSurveyByToken(String publicToken) {
        Survey survey = surveyRepository.findByPublicTokenAndStatus(
                        publicToken,
                        SurveyStatus.PUBLISHED
                )
                .orElseThrow(() -> new ResourceNotFoundException("Published survey not found"));

        if (!survey.isWithinValidityPeriod(DateTimeUtil.today())) {
            throw new ResourceNotFoundException("Survey is not available currently");
        }

        return SurveyMapper.toSurveyResponseDto(
                survey,
                surveyResponseRepository.countBySurveyId(survey.getId())
        );
    }

    private Survey getSurveyOrThrow(Long surveyId) {
        return surveyRepository.findById(surveyId)
                .filter(survey -> !SurveyStatus.DELETED.equals(survey.getStatus()))
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id: " + surveyId));
    }

    private void addQuestionsToSurvey(
            Survey survey,
            List<QuestionRequestDto> questionRequestDtos
    ) {
        if (questionRequestDtos == null || questionRequestDtos.isEmpty()) {
            return;
        }

        for (QuestionRequestDto questionRequestDto : questionRequestDtos) {
            surveyValidator.validateQuestionRequest(questionRequestDto);

            Question question = Question.builder()
                    .survey(survey)
                    .questionText(questionRequestDto.getQuestionText())
                    .questionType(questionRequestDto.getQuestionType())
                    .dataType(questionRequestDto.getDataType())
                    .required(questionRequestDto.getRequired())
                    .displayOrder(questionRequestDto.getDisplayOrder())
                    .minValue(questionRequestDto.getMinValue())
                    .maxValue(questionRequestDto.getMaxValue())
                    .build();

            addOptionsToQuestion(question, questionRequestDto.getOptions());
            survey.getQuestions().add(question);
        }
    }

    private void addOptionsToQuestion(
            Question question,
            List<OptionRequestDto> optionRequestDtos
    ) {
        if (optionRequestDtos == null || optionRequestDtos.isEmpty()) {
            return;
        }

        for (OptionRequestDto optionRequestDto : optionRequestDtos) {
            QuestionOption option = QuestionOption.builder()
                    .question(question)
                    .optionLabel(optionRequestDto.getOptionLabel())
                    .optionValue(optionRequestDto.getOptionValue())
                    .displayOrder(optionRequestDto.getDisplayOrder())
                    .build();

            question.getOptions().add(option);
        }
    }
}