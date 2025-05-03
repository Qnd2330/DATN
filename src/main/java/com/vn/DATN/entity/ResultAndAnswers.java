package com.vn.DATN.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "result_answer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultAndAnswers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "surveyResultId", referencedColumnName = "id")
    private SurveyResult surveyResult;
    @ManyToOne
    @JoinColumn(name = "userAnswerId", referencedColumnName = "userAnswerId")
    private UserAnswer userAnswer;
}
