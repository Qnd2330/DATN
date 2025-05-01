package com.vn.DATN.Service;

import com.vn.DATN.Models.Answer;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionAndAnswerService {
    List<Answer> findAnswersByQuestionId(Integer questionId);
    void deleteByQuestionId(Integer questionId);
}
