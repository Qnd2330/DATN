package com.vn.DATN.DTO.request;

import com.vn.DATN.DTO.request.AnswerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Integer questionId;
    private String questionText;
    private String type;
    private List<AnswerDTO> answers;
}
