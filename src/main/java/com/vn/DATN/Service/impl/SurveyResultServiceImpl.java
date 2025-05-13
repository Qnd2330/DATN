package com.vn.DATN.Service.impl;

import com.vn.DATN.Common.BasicBeanRemote;
import com.vn.DATN.DTO.request.SubmissionDTO;
import com.vn.DATN.DTO.request.UserAnswerDTO;
import com.vn.DATN.Service.SurveyResultService;;
import com.vn.DATN.Service.repositories.*;
import com.vn.DATN.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyResultServiceImpl implements SurveyResultService {
    private final SurveyResultRepo surveyResultRepo;
    private final QuestionAndAnswerRepo questionAndAnswerRepo;
    private final UserAndAnswersRepo userAndAnswersRepo;
    private final BasicBeanRemote basicBeanRemote;

    @Override
    public SurveyResult submitSurvey(SubmissionDTO submissionDTO) {
        Users user = basicBeanRemote.find(Users.class, submissionDTO.getUserId());
        if (user == null) {
            throw new RuntimeException("Người dùng không tồn tại");
        }
        Survey survey = basicBeanRemote.find(Survey.class, submissionDTO.getSurveyId());
        if (survey == null) {
            throw new RuntimeException("Bản báo cáo không tồn tại");
        }
        SurveyResult surveyResult = SurveyResult.builder()
                .users(user)
                .survey(survey)
                .build();
        surveyResultRepo.save(surveyResult);

        for (UserAnswerDTO userAnswerDTO : submissionDTO.getUserAnswers()) {
            Question question = basicBeanRemote.find(Question.class, userAnswerDTO.getQuestionId());
            if (question == null) {
                throw new RuntimeException("Câu hỏi không tồn tại");
            }
            Answer answer = basicBeanRemote.find(Answer.class, userAnswerDTO.getAnswerId());
            if (answer == null) {
                throw new RuntimeException("Câu trả lời không tồn tại");
            }

            QuestionAnswer questionAnswer = questionAndAnswerRepo
                    .findByQuestionAndAnswer(question, answer)
                    .orElseThrow(() -> new RuntimeException("Question-Answer pair not found"));

            UserAnswer userAnswer = UserAnswer.builder()
                    .surveyResult(surveyResult)
                    .questionAnswer(questionAnswer)
                    .build();
            userAndAnswersRepo.save(userAnswer);
        }

        return surveyResult;
    }

    @Override
    public SurveyResult get(Integer id) {
        return surveyResultRepo.findById(id).orElse(null);
    }

    @Override
    public SurveyResult deleteSurvey(Integer id) {
        return null;
    }
}
