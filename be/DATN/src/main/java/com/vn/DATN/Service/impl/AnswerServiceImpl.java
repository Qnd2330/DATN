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
        Answer answer = answerRepo.findByContent(request.getContent());
        if (answer != null) {
            return answer;
        }
        answer = Answer.builder()
                .content(request.getContent())
                .build();
        return answerRepo.save(answer);
    }

    @Override
    @Transactional
    public Answer edit(AnswerDTO request) {
        Answer answer = answerRepo.getReferenceById(request.getAnswerId());
        if (answer == null) {
            throw new RuntimeException("Không tìm thấy câu trả lời với ID: " + request.getAnswerId());
        }
        answer.setContent(request.getContent());
        return answerRepo.saveAndFlush(answer);
    }

    @Override
    public Answer findById(Integer answerId) {
        return answerRepo.findById(answerId).orElse(null);
    }

    @Override
    @Transactional
    public boolean deleteById(Integer answerId) {
        if (!answerRepo.existsById(answerId)) {
            return false;
        }
        answerRepo.deleteById(answerId);
        return true;
    }
}
