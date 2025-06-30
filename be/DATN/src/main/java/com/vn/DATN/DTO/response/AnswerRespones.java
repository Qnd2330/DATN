package com.vn.DATN.DTO.response;

import com.vn.DATN.entity.Answer;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerRespones {
    Integer answerId;
    String content;
    Integer point;

    public static AnswerRespones fromAnswer (Answer answer){
        AnswerRespones answerRespones = AnswerRespones.builder()
                .answerId(answer.getAnswerId())
                .content(answer.getContent())
                .point(answer.getPoint())
                .build();
        return answerRespones;
    }
}
