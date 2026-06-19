package com.capstone.crms.controller;

import com.capstone.crms.dto.SurveyRequest;
import com.capstone.crms.entity.Survey;
import com.capstone.crms.service.SurveyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    @GetMapping("/api/surveys")
    public List<Survey> all() { return surveyService.all(); }

    @GetMapping("/api/surveys/{id}")
    public Survey one(@PathVariable Long id) { return surveyService.byId(id); }

    @PostMapping("/api/surveys")
    public Survey create(@Valid @RequestBody SurveyRequest request) { return surveyService.create(request); }

    @PutMapping("/api/surveys/{id}")
    public Survey update(@PathVariable Long id, @Valid @RequestBody SurveyRequest request) { return surveyService.update(id, request); }

    @PutMapping("/api/surveys/{id}/publish")
    public Survey publish(@PathVariable Long id) { return surveyService.publish(id); }

    @DeleteMapping("/api/surveys/{id}")
    public void delete(@PathVariable Long id) { surveyService.delete(id); }

    @GetMapping("/api/public/surveys/{token}")
    public Survey publicSurvey(@PathVariable String token) { return surveyService.byToken(token); }
}
