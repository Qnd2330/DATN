package com.vn.DATN.Service.impl;

import com.vn.DATN.Common.QuestionType;
import com.vn.DATN.DTO.request.QuestionDTO;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionAndAnswerServiceImpl implements QuestionAndAnswerService {
    private final QuestionAndAnswerRepo questionAndAnswerRepo;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Override
    @Transactional
    public List<QuestionAnswer> create(List<QuestionDTO> request) {
        List<QuestionAnswer> questionAnswers = new ArrayList<>();
        for (QuestionDTO questionDTO : request) {
            Question question = getQuestion(questionDTO);

            if (QuestionType.SingleChoice.toString().equals(questionDTO.getType())) {
                questionAnswers.addAll(
                        buildQuestionAnswersForMultipleChoice(question, questionDTO.getAnswers(), false)
                );
            } else if (QuestionType.Essay.toString().equals(questionDTO.getType())) {
                questionAnswers.add(buildQuestionAnswerForEssay(question));
            }
        }

        List<QuestionAnswer> toSave = questionAnswers.stream()
                .filter(qa -> qa.getId() == null)
                .toList();

        if (!toSave.isEmpty()) {
            questionAndAnswerRepo.saveAll(toSave);
        }
        return questionAnswers;
    }

    @Override
    @Transactional
    public List<QuestionAnswer> update(List<QuestionDTO> request) {
        List<QuestionAnswer> updatedQAList = new ArrayList<>();

        for (QuestionDTO questionDTO : request) {
            Question question = questionService.edit(questionDTO);

            if (QuestionType.SingleChoice.toString().equals(questionDTO.getType())) {
                updatedQAList.addAll(
                        buildQuestionAnswersForMultipleChoice(question, questionDTO.getAnswers(), true)
                );
            } else if (QuestionType.Essay.toString().equals(questionDTO.getType())) {
                updatedQAList.add(buildQuestionAnswerForEssay(question));
            }
        }

        List<QuestionAnswer> toSave = updatedQAList.stream()
                .filter(qa -> qa.getId() == null)
                .toList();

        if (!toSave.isEmpty()) {
            questionAndAnswerRepo.saveAll(toSave);
        }

        return updatedQAList;
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
        Question question ;
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
        if (!questionService.existsById(questionId)) {
            throw new RuntimeException("Câu hỏi không tồn tại");
        }
        int deletedRelations = questionAndAnswerRepo.deleteByQuestionId(questionId);
        if (deletedRelations < 0) {
            throw new RuntimeException("Xóa quan hệ Question-Answer thất bại");
        }
        return true;
    }

    private List<QuestionAnswer> buildQuestionAnswersForMultipleChoice(Question question, List<AnswerDTO> answers, boolean isUpdate) {
        List<QuestionAnswer> result = new ArrayList<>();
        for (AnswerDTO answerDTO : answers) {
            Answer answer = isUpdate ? answerService.edit(answerDTO) : getAnswer(answerDTO);
            Optional<QuestionAnswer> existingQA = questionAndAnswerRepo.findByQuestionAndAnswer(question, answer);
            result.add(existingQA.orElseGet(() ->
                    QuestionAnswer.builder()
                            .question(question)
                            .answer(answer)
                            .build()));
        }
        return result;
    }

    private QuestionAnswer buildQuestionAnswerForEssay(Question question) {
        Answer answer = answerService.findById(0); // default essay answer
        return questionAndAnswerRepo.findByQuestionAndAnswer(question, answer)
                .orElseGet(() -> QuestionAnswer.builder()
                        .question(question)
                        .answer(answer)
                        .build());
    }
}
