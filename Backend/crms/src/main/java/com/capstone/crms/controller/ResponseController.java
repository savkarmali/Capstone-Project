package com.capstone.crms.controller;

import com.capstone.crms.dto.ResponseRequest;
import com.capstone.crms.entity.SurveyResponse;
import com.capstone.crms.service.ResponseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService responseService;

    @PostMapping("/api/public/surveys/{token}/responses")
    public SurveyResponse submit(@PathVariable String token, @Valid @RequestBody ResponseRequest request) {
        return responseService.submit(token, request);
    }

    @GetMapping("/api/responses")
    public List<SurveyResponse> search(@RequestParam(required = false) Long surveyId,
                                       @RequestParam(required = false) String q,
                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return responseService.search(surveyId, q, from, to);
    }

    @GetMapping(value = "/api/responses/export", produces = "text/csv")
    public ResponseEntity<String> export(@RequestParam(required = false) Long surveyId,
                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=survey-responses.csv")
                .body(responseService.csv(surveyId, from, to));
    }
}
