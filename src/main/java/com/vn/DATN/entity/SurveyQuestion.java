package com.vn.DATN.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "survey_question")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurveyQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "surveyId", referencedColumnName = "surveyId")
    private Survey survey;
    @ManyToOne
    @JoinColumn(name = "question_answer_id", referencedColumnName = "id")
    private QuestionAnswer questionAnswer;
}
