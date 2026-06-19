package com.capstone.survey.service.impl;

import com.capstone.survey.dto.request.OptionRequestDto;
import com.capstone.survey.dto.request.QuestionRequestDto;
import com.capstone.survey.dto.response.QuestionResponseDto;
import com.capstone.survey.entity.Question;
import com.capstone.survey.entity.QuestionOption;
import com.capstone.survey.entity.Survey;
import com.capstone.survey.enums.SurveyStatus;
import com.capstone.survey.exception.BadRequestException;
import com.capstone.survey.exception.ResourceNotFoundException;
import com.capstone.survey.mapper.SurveyMapper;
import com.capstone.survey.repository.QuestionRepository;
import com.capstone.survey.repository.SurveyRepository;
import com.capstone.survey.service.QuestionService;
import com.capstone.survey.util.ApiConstants;
import com.capstone.survey.validation.SurveyValidator;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final SurveyRepository surveyRepository;
    private final SurveyValidator surveyValidator;

    public QuestionServiceImpl(
            QuestionRepository questionRepository,
            SurveyRepository surveyRepository,
            SurveyValidator surveyValidator
    ) {
        this.questionRepository = questionRepository;
        this.surveyRepository = surveyRepository;
        this.surveyValidator = surveyValidator;
    }

    @Override
    @Transactional
    public QuestionResponseDto addQuestion(
            Long surveyId,
            QuestionRequestDto questionRequestDto
    ) {
        Survey survey = getSurveyOrThrow(surveyId);

        surveyValidator.validateSurveyEditable(survey);
        surveyValidator.validateQuestionRequest(questionRequestDto);

        if (questionRepository.countBySurveyId(surveyId) >= ApiConstants.MAX_QUESTIONS_PER_SURVEY) {
            throw new BadRequestException("Survey can have maximum 6 questions");
        }

        if (questionRepository.existsBySurveyIdAndDisplayOrder(
                surveyId,
                questionRequestDto.getDisplayOrder()
        )) {
            throw new BadRequestException("Question display order already exists");
        }

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

        addOptions(question, questionRequestDto.getOptions());

        Question savedQuestion = questionRepository.save(question);

        return SurveyMapper.toQuestionResponseDto(savedQuestion);
    }

    @Override
    @Transactional
    public QuestionResponseDto updateQuestion(
            Long questionId,
            QuestionRequestDto questionRequestDto
    ) {
        Question question = getQuestionOrThrow(questionId);

        surveyValidator.validateSurveyEditable(question.getSurvey());
        surveyValidator.validateQuestionRequest(questionRequestDto);

        question.setQuestionText(questionRequestDto.getQuestionText());
        question.setQuestionType(questionRequestDto.getQuestionType());
        question.setDataType(questionRequestDto.getDataType());
        question.setRequired(questionRequestDto.getRequired());
        question.setDisplayOrder(questionRequestDto.getDisplayOrder());
        question.setMinValue(questionRequestDto.getMinValue());
        question.setMaxValue(questionRequestDto.getMaxValue());

        question.getOptions().clear();
        addOptions(question, questionRequestDto.getOptions());

        Question updatedQuestion = questionRepository.save(question);

        return SurveyMapper.toQuestionResponseDto(updatedQuestion);
    }

    @Override
    @Transactional
    public void deleteQuestion(Long questionId) {
        Question question = getQuestionOrThrow(questionId);

        surveyValidator.validateSurveyEditable(question.getSurvey());

        questionRepository.delete(question);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionResponseDto> getQuestionsBySurvey(Long surveyId) {
        getSurveyOrThrow(surveyId);

        return questionRepository.findBySurveyIdOrderByDisplayOrderAsc(surveyId)
                .stream()
                .map(SurveyMapper::toQuestionResponseDto)
                .toList();
    }

    private Survey getSurveyOrThrow(Long surveyId) {
        return surveyRepository.findById(surveyId)
                .filter(survey -> !SurveyStatus.DELETED.equals(survey.getStatus()))
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id: " + surveyId));
    }

    private Question getQuestionOrThrow(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));
    }

    private void addOptions(
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