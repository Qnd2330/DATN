package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.QuestionDTO;
import com.vn.DATN.entity.Question;

public interface QuestionService {
    Question create(QuestionDTO questionDTO);

    Question edit(QuestionDTO questionDTO);

    Question findById(Integer questionId);

    boolean existsById(Integer questionId);

    boolean delete(Integer questionId);

}
