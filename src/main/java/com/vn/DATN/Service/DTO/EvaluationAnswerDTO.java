package com.vn.DATN.Service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationAnswerDTO {
    private int answerId;
    private int  evaluationFormId;
    private int  questionId;
    private List<Integer> choiceId;
    private int scoreGiven;
}
