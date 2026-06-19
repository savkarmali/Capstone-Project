package com.capstone.crms.controller;

import com.capstone.crms.repository.ResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final com.capstone.crms.repository.SurveyRepository serveyRepository;
    private final ResponseRepository responseRepository;

    @GetMapping
    public Map<String, Object> dashboard() {
        LocalDate today = LocalDate.now();
        long total = serveyRepository.count();
        long active = serveyRepository.findAll().stream()
                .filter(s -> "PUBLISHED".equals(s.getStatus()) && !today.isBefore(s.getStartDate()) && !today.isAfter(s.getEndDate()))
                .count();
        long expired = serveyRepository.findAll().stream()
                .filter(s -> today.isAfter(s.getEndDate()))
                .count();
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("totalSurveys", total);
        data.put("activeSurveys", active);
        data.put("expiredSurveys", expired);
        data.put("totalResponses", responseRepository.count());
        data.put("trend", responseRepository.findAll().stream()
                .collect(Collectors.groupingBy(r -> r.getSubmittedAt().toLocalDate(), TreeMap::new, Collectors.counting()))
                .entrySet().stream().map(e -> List.of(e.getKey().toString(), e.getValue())).toList());
        data.put("surveyStatus", Map.of("draft", serveyRepository.countByStatus("DRAFT"), "published", serveyRepository.countByStatus("PUBLISHED")));
        return data;
    }
}
