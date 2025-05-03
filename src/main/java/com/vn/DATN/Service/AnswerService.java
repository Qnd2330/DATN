package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.AnswerDTO;
import com.vn.DATN.entity.Answer;

public interface AnswerService {
    Answer create(AnswerDTO answerDTO);

    Answer edit(AnswerDTO answerDTO);

    Answer findById(Integer answerId);

    boolean deleteById(Integer answerId);

}
