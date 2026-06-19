package com.capstone.survey.service.impl;

import com.capstone.survey.entity.Answer;
import com.capstone.survey.entity.Question;
import com.capstone.survey.entity.Survey;
import com.capstone.survey.entity.SurveyResponse;
import com.capstone.survey.enums.SurveyStatus;
import com.capstone.survey.exception.BadRequestException;
import com.capstone.survey.exception.ResourceNotFoundException;
import com.capstone.survey.repository.QuestionRepository;
import com.capstone.survey.repository.SurveyRepository;
import com.capstone.survey.repository.SurveyResponseRepository;
import com.capstone.survey.service.ExportService;
import com.capstone.survey.util.DateTimeUtil;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExportServiceImpl implements ExportService {

    private static final int EXPORT_PAGE_SIZE = 10_000;

    private final SurveyRepository surveyRepository;
    private final SurveyResponseRepository surveyResponseRepository;
    private final QuestionRepository questionRepository;

    public ExportServiceImpl(
            SurveyRepository surveyRepository,
            SurveyResponseRepository surveyResponseRepository,
            QuestionRepository questionRepository
    ) {
        this.surveyRepository = surveyRepository;
        this.surveyResponseRepository = surveyResponseRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] exportSurveyResponsesToExcel(
            Long surveyId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        Survey survey = getSurveyOrThrow(surveyId);
        List<Question> questions = questionRepository.findBySurveyIdOrderByDisplayOrderAsc(surveyId);
        List<SurveyResponse> responses = getResponses(surveyId, startDate, endDate);

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Survey Responses");
            createHeaderRow(sheet, questions);

            int rowIndex = 1;
            for (SurveyResponse response : responses) {
                createDataRow(sheet, rowIndex, response, questions);
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (Exception exception) {
            throw new BadRequestException("Failed to export Excel for survey: " + survey.getTitle());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] exportSurveyResponsesToCsv(
            Long surveyId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        getSurveyOrThrow(surveyId);
        List<Question> questions = questionRepository.findBySurveyIdOrderByDisplayOrderAsc(surveyId);
        List<SurveyResponse> responses = getResponses(surveyId, startDate, endDate);

        StringBuilder csv = new StringBuilder();
        csv.append("First Name,Last Name,Email,Submitted At");

        for (Question question : questions) {
            csv.append(",").append(escapeCsv(question.getQuestionText()));
        }

        csv.append("\n");

        for (SurveyResponse response : responses) {
            csv.append(escapeCsv(response.getRespondentFirstName())).append(",");
            csv.append(escapeCsv(response.getRespondentLastName())).append(",");
            csv.append(escapeCsv(response.getRespondentEmail())).append(",");
            csv.append(response.getSubmittedAt());

            Map<Long, Answer> answerMap = response.getAnswers()
                    .stream()
                    .collect(Collectors.toMap(answer -> answer.getQuestion().getId(), Function.identity()));

            for (Question question : questions) {
                Answer answer = answerMap.get(question.getId());
                csv.append(",").append(escapeCsv(answer == null ? "" : answer.getAnswerValue()));
            }

            csv.append("\n");
        }

        return csv.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] exportSurveyResponsesToPdf(
            Long surveyId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        Survey survey = getSurveyOrThrow(surveyId);
        List<Question> questions = questionRepository.findBySurveyIdOrderByDisplayOrderAsc(surveyId);
        List<SurveyResponse> responses = getResponses(surveyId, startDate, endDate);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            document.add(new Paragraph("Survey Responses"));
            document.add(new Paragraph("Survey: " + survey.getTitle()));
            document.add(new Paragraph("Total Responses: " + responses.size()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4 + questions.size());
            table.addCell("First Name");
            table.addCell("Last Name");
            table.addCell("Email");
            table.addCell("Submitted At");

            for (Question question : questions) {
                table.addCell(question.getQuestionText());
            }

            for (SurveyResponse response : responses) {
                table.addCell(response.getRespondentFirstName());
                table.addCell(response.getRespondentLastName() == null ? "" : response.getRespondentLastName());
                table.addCell(response.getRespondentEmail());
                table.addCell(response.getSubmittedAt().toString());

                Map<Long, Answer> answerMap = response.getAnswers()
                        .stream()
                        .collect(Collectors.toMap(answer -> answer.getQuestion().getId(), Function.identity()));

                for (Question question : questions) {
                    Answer answer = answerMap.get(question.getId());
                    table.addCell(answer == null ? "" : answer.getAnswerValue());
                }
            }

            document.add(table);
            document.close();

            return outputStream.toByteArray();
        } catch (Exception exception) {
            throw new BadRequestException("Failed to export PDF for survey: " + survey.getTitle());
        }
    }

    private Survey getSurveyOrThrow(Long surveyId) {
        return surveyRepository.findById(surveyId)
                .filter(survey -> !SurveyStatus.DELETED.equals(survey.getStatus()))
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id: " + surveyId));
    }

    private List<SurveyResponse> getResponses(
            Long surveyId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        if (startDate != null && endDate != null) {
            if (DateTimeUtil.isEndDateBeforeStartDate(startDate, endDate)) {
                throw new BadRequestException("End date cannot be before start date");
            }

            return surveyResponseRepository.findBySurveyIdAndSubmittedAtBetween(
                            surveyId,
                            DateTimeUtil.startOfDay(startDate),
                            DateTimeUtil.endOfDay(endDate),
                            PageRequest.of(0, EXPORT_PAGE_SIZE)
                    )
                    .getContent();
        }

        return surveyResponseRepository.findBySurveyId(
                        surveyId,
                        PageRequest.of(0, EXPORT_PAGE_SIZE)
                )
                .getContent();
    }

    private void createHeaderRow(
            Sheet sheet,
            List<Question> questions
    ) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("First Name");
        row.createCell(1).setCellValue("Last Name");
        row.createCell(2).setCellValue("Email");
        row.createCell(3).setCellValue("Submitted At");

        int cellIndex = 4;
        for (Question question : questions) {
            row.createCell(cellIndex).setCellValue(question.getQuestionText());
        }
    }

    private void createDataRow(
            Sheet sheet,
            int rowIndex,
            SurveyResponse response,
            List<Question> questions
    ) {
        Row row = sheet.createRow(rowIndex);
        row.createCell(0).setCellValue(response.getRespondentFirstName());
        row.createCell(1).setCellValue(response.getRespondentLastName());
        row.createCell(2).setCellValue(response.getRespondentEmail());
        row.createCell(3).setCellValue(response.getSubmittedAt().toString());

        Map<Long, Answer> answerMap = response.getAnswers()
                .stream()
                .collect(Collectors.toMap(answer -> answer.getQuestion().getId(), Function.identity()));

        int cellIndex = 4;
        for (Question question : questions) {
            Answer answer = answerMap.get(question.getId());
            Cell cell = row.createCell(cellIndex);
            cell.setCellValue(answer == null ? "" : answer.getAnswerValue());
        }
    }

    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }

        String escaped = value.replace("\"", "\"\"");

        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n")) {
            return "\"" + escaped + "\"";
        }

        return escaped;
    }
}