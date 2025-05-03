package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.AnswerDTO;
import com.vn.DATN.entity.Answer;
import com.vn.DATN.Service.AnswerService;
import com.vn.DATN.Service.repositories.AnswerRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepo answerRepo;

    @Override
    @Transactional
    public Answer create(AnswerDTO request) {
        Answer answer = answerRepo.findByAnswerText(request.getAnswerText());
        if (answer != null) {
            return answer;
        }
        answer = Answer.builder()
                .answerText(request.getAnswerText())
                .point(request.getPoint())
                .build();
        return answerRepo.save(answer);
    }

    @Override
    @Transactional
    public Answer edit(AnswerDTO request) {
        Answer answer = answerRepo.getReferenceById(request.getAnswerId());
        if (answer == null) {
            return answer;
        }
        answer.setAnswerText(request.getAnswerText());
        answer.setPoint(request.getPoint());
        return answerRepo.saveAndFlush(answer);
    }

    @Override
    public Answer findById(Integer answerId) {
        return answerRepo.findById(answerId).orElse(null);
    }

    @Override
    public boolean deleteById(Integer answerId) {
        if (!answerRepo.existsById(answerId)) {
            return false;
        }
        answerRepo.deleteById(answerId);
        return true;
    }
}
