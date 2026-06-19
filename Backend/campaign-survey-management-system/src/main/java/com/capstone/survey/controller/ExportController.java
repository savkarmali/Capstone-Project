package com.capstone.survey.controller;

import com.capstone.survey.service.ExportService;
import com.capstone.survey.util.ApiConstants;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstants.EXPORTS_BASE)
public class ExportController {

    private final ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping("/survey/{surveyId}/excel")
    public ResponseEntity<byte[]> exportSurveyResponsesToExcel(
            @PathVariable Long surveyId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ) {
        byte[] fileContent = exportService.exportSurveyResponsesToExcel(
                surveyId,
                startDate,
                endDate
        );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=survey-responses-" + surveyId + ".xlsx"
                )
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileContent);
    }

    @GetMapping("/survey/{surveyId}/csv")
    public ResponseEntity<byte[]> exportSurveyResponsesToCsv(
            @PathVariable Long surveyId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ) {
        byte[] fileContent = exportService.exportSurveyResponsesToCsv(
                surveyId,
                startDate,
                endDate
        );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=survey-responses-" + surveyId + ".csv"
                )
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(fileContent);
    }

    @GetMapping("/survey/{surveyId}/pdf")
    public ResponseEntity<byte[]> exportSurveyResponsesToPdf(
            @PathVariable Long surveyId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ) {
        byte[] fileContent = exportService.exportSurveyResponsesToPdf(
                surveyId,
                startDate,
                endDate
        );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=survey-responses-" + surveyId + ".pdf"
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(fileContent);
    }
}