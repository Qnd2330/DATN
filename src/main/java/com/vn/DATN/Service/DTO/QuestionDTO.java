package com.vn.DATN.Service.DTO;

import com.vn.DATN.Models.Answer;
import com.vn.DATN.Models.UserAnswer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private String questionText;
    private String type;
    private List<AnswerDTO> answers;
}
