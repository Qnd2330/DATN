package com.vn.DATN.DTO.request;

import com.vn.DATN.entity.QuestionAnswer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswerDTO {
    private Integer userAnswerId;
    private Integer questionId;
    private Integer answerId;
    private String essayContent;
}
