package com.vn.DATN.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "survey_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SurveyResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Users users;
    @ManyToOne
    @JoinColumn(name = "evaluator_id")
    private Users evaluator;
    @ManyToOne
    @JoinColumn(name = "surveyId", referencedColumnName = "surveyId")
    private Survey survey;
    @Column(name = "create_at")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
