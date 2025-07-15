package com.vn.DATN.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswerDTO {
    private Integer userAnswerId;
    private Integer questionId;
    private Integer answerId;
    private String essayContent;
}
