package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.mapper.QuestionMapper;
import com.vn.DATN.DTO.request.QuestionDTO;
import com.vn.DATN.DTO.mapper.QuestionAnswerMapper;
import com.vn.DATN.DTO.request.AnswerDTO;
import com.vn.DATN.Service.AnswerService;
import com.vn.DATN.Service.QuestionAndAnswerService;
import com.vn.DATN.Service.QuestionService;
import com.vn.DATN.Service.repositories.QuestionAndAnswerRepo;
import com.vn.DATN.entity.Answer;
import com.vn.DATN.entity.Question;
import com.vn.DATN.entity.QuestionAnswer;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionAndAnswerServiceImpl implements QuestionAndAnswerService {
    private final QuestionAndAnswerRepo questionAndAnswerRepo;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Override
    @Transactional
    public List<QuestionDTO> create(List<QuestionDTO> request) {
        List<QuestionAnswer> questionAnswers = new ArrayList<>();
        for (QuestionDTO questionDTO : request) {
            Question question = getQuestion(questionDTO);
            for (AnswerDTO answerDTO : questionDTO.getAnswers()) {
                questionAnswers.add(QuestionAnswer.builder()
                        .question(question)
                        .answer(getAnswer(answerDTO))
                        .build());
            }
        }

        List<QuestionAnswer> questionAnswersList = questionAndAnswerRepo.saveAll(questionAnswers);
        return QuestionAnswerMapper.INSTANCE.toQuestionDTOList(questionAnswersList);
    }
    @Override
    @Transactional
    public Answer getAnswer(AnswerDTO answerDTO) {
        Answer answer;
        if (answerDTO.getAnswerId() == null) {
            answer = answerService.create(answerDTO);
        } else {
            try {
                answer = answerService.findById(answerDTO.getAnswerId());
            } catch (EntityNotFoundException ex) {
                throw new RuntimeException("Không tìm thấy câu trả lời với ID: " + answerDTO.getAnswerId());
            }
        }
        return answer;
    }
    @Override
    @Transactional
    public Question getQuestion(QuestionDTO questionDTO) {
//        Hàm này thấy có 1 vđ nữa,
        Question question = new Question();
        if (questionDTO.getQuestionId() == null) {
            question = questionService.create(questionDTO);
        } else {
            try {
                return questionService.findById(questionDTO.getQuestionId());
            } catch (EntityNotFoundException ex) {
                throw new RuntimeException("Không tìm thấy câu hỏi với ID: " + questionDTO.getQuestionId());
            }
        }
        return question;
    }

    @Override
    public List<Answer> findAnswersByQuestionId(Integer questionId) {
        questionService.existsById(questionId);
        return questionAndAnswerRepo.findAnswersByQuestionId(questionId);
    }

    @Override
    @Transactional
    public boolean deleteByQuestionId(Integer questionId) {
        boolean isDelete;
        if (!questionService.existsById(questionId)) {
            throw new RuntimeException("Câu hỏi không tồn tại với ID: " + questionId);
        }
        List<Answer> listAnswers = findAnswersByQuestionId(questionId);

        int deleted = questionAndAnswerRepo.deleteByQuestionId(questionId);
        if(deleted < 0) {
            throw new RuntimeException("Xóa quan hệ Question-Answer thất bại");
        }

        for (Answer answers : listAnswers) {
            isDelete =  answerService.deleteById(answers.getAnswerId());
           if (!isDelete) {
               throw new RuntimeException("Xóa câu trả lời thất bại với ID: " + answers.getAnswerId());
           }
        }

        isDelete = questionService.delete(questionId);
        if (!isDelete) {
            throw new RuntimeException("Xóa câu hỏi thất bại");
        }
        return true;
    }
}
