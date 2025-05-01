package com.vn.DATN.Service.DTO;

import com.vn.DATN.Models.UserAnswer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationFormDTO {
    private int evaluationId;
    private List<UserAnswer> answers;
}
