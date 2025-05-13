package com.vn.DATN.DTO.respones;

import com.vn.DATN.entity.Answer;
import com.vn.DATN.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionAnswerRespones {
    QuestionRespones questionRespones;
    List<AnswerRespones> answer;

}
