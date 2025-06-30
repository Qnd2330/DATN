package com.vn.DATN.DTO.response;

import com.vn.DATN.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponse {
    Integer questionId;
    String question;
    String type;

    public static QuestionResponse fromQuestion(Question question) {
        return QuestionResponse.builder()
                .questionId(question.getQuestionId())
                .question(question.getQuestionText())
                .type(question.getType())
                .build();
    }
}
