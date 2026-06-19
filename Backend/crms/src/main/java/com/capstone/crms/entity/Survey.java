package com.capstone.crms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "surveys")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private String status;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(unique = true)
    private String publicToken;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position asc")
    private List<Question> questions = new ArrayList<>();
}
