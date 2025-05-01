package com.vn.DATN.Service;

import com.vn.DATN.Models.Answer;
import com.vn.DATN.Service.Repositories.QuestionAndAnswerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionAndAnswerServiceImpl implements QuestionAndAnswerService {
    private final QuestionAndAnswerRepo questionAndAnswerRepo;

    @Override
    public List<Answer> findAnswersByQuestionId(Integer questionId) {
        if(questionId == null) {
            System.out.println("Question id không tồn tại!");
            return null;
        }
        return questionAndAnswerRepo.findAnswersByQuestionId(questionId);
    }

    @Override
    public void deleteByQuestionId(Integer questionId) {
        questionAndAnswerRepo.deleteByQuestionId(questionId);
    }
}
