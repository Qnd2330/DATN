package com.vn.DATN.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionAnswerResponse {
    QuestionResponse questionResponse;
    List<AnswerRespones> answer;

}
