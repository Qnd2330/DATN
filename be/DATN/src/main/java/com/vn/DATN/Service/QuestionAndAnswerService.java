package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.AnswerDTO;
import com.vn.DATN.DTO.request.QuestionDTO;
import com.vn.DATN.entity.Answer;
import com.vn.DATN.entity.Question;
import com.vn.DATN.entity.QuestionAnswer;

import java.util.List;

public interface QuestionAndAnswerService {
    List<QuestionAnswer> create(List<QuestionDTO> questionDTO);

    List<QuestionAnswer> update(List<QuestionDTO> request);

    Question getQuestion(QuestionDTO questionDTO);

    Answer getAnswer(AnswerDTO answerDTO);

    List<Answer> findAnswersByQuestionId(Integer questionId);

    boolean deleteByQuestionId(Integer questionId);
}
