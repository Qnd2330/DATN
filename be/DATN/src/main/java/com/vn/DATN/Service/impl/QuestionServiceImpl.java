package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.QuestionDTO;
import com.vn.DATN.Service.QuestionService;
import com.vn.DATN.Service.repositories.QuestionRepo;
import com.vn.DATN.entity.Question;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepo questionRepo;

    @Override
    @Transactional
    public Question create(QuestionDTO request) {
        Question question = questionRepo.findQuestionByQuestionText(request.getQuestionText());
        if (question != null) {
            return question;
        }
        question = Question.builder()
                .questionText(request.getQuestionText())
                .type(request.getType())
                .build();
        return questionRepo.save(question);
    }

    @Override
    @Transactional
    public Question edit(QuestionDTO request) {
        Question question = findById(request.getQuestionId());
        if (question == null) {
            throw new RuntimeException("Không tìm thấy câu hỏi với ID: " + request.getQuestionId());
        }
        question.setQuestionText(request.getQuestionText());
        question.setType(request.getType());
        return questionRepo.saveAndFlush(question);
    }

    @Override
    public Question findById(Integer questionId) {
        return questionRepo.findById(questionId).orElse(null);
    }

    @Override
    public boolean existsById(Integer questionId) {
        if (questionId == null) {
            return false;
        }
        return questionRepo.existsById(questionId);
    }

    @Override
    @Transactional
    public boolean delete(Integer questionId) {
        if (!questionRepo.existsById(questionId)) {
            return false;
        }
        questionRepo.deleteById(questionId);
        return true;
    }
}
