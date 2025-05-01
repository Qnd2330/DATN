package com.vn.DATN.Controller;

import com.vn.DATN.Common.BasicBeanRemote;
import com.vn.DATN.Models.Answer;
import com.vn.DATN.Models.Question;
import com.vn.DATN.Models.QuestionAnswer;
import com.vn.DATN.Service.DTO.AnswerDTO;
import com.vn.DATN.Service.DTO.QuestionDTO;
import com.vn.DATN.Service.QuestionAndAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerControler {
    private final BasicBeanRemote basicBeanRemote;
    private final QuestionAndAnswerService questionAndAnswerService;

    @GetMapping("/list")
    public List<Answer> list() {
        return basicBeanRemote.findAll(Answer.class);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody QuestionDTO questionDTO) {
        Question question = new Question();
        question.setQuestionText("Giáo viên giảng dễ hiểu chứ");
        question.setType("MULTIPLE CHOICE");
        basicBeanRemote.create(question);
        if (questionDTO.getAnswers() != null) {
            for (AnswerDTO answerDTO : questionDTO.getAnswers()) {
                Answer answer = new Answer();
                answer.setAnswerText(answerDTO.getAnswerText());
                answer.setPoint(answerDTO.getPoint());

                basicBeanRemote.create(answer);
                QuestionAnswer qa = new QuestionAnswer();
                qa.setQuestion(question);
                qa.setAnswer(answer);
                basicBeanRemote.create(qa);
            }
        } else {
            System.out.println("Danh sách câu trả lời đang bị null!");
        }
        return ResponseEntity.ok("Tạo mới thành công");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update (@PathVariable Integer id, @RequestBody QuestionDTO questionDTO) {
        // Lấy câu hỏi hiện có
        Question question = basicBeanRemote.find(Question.class,id);

        // Cập nhật thông tin câu hỏi
        question.setQuestionText(questionDTO.getQuestionText());
        question.setType(questionDTO.getType());
        basicBeanRemote.edit(question);

        // Cập nhật danh sách câu trả lời
            for (AnswerDTO answers : questionDTO.getAnswers()) {
                if (answers.getAnswerId() != null) {
                    Answer answer = basicBeanRemote.find(Answer.class,answers.getAnswerId());
                    if (answer != null) {
                        answer.setAnswerText(answers.getAnswerText());
                        answer.setPoint(answers.getPoint());
                        basicBeanRemote.edit(answer);
                    } else {
                        return ResponseEntity.badRequest().body("Không tìm thấy câu trả lời với ID: " + answers.getAnswerId());
                    }
                }
            }

        return ResponseEntity.ok("Cập nhật thành công");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete (@PathVariable Integer id) {
        // Tìm answerId theo questionId
        List<Answer> listAnswers = questionAndAnswerService.findAnswersByQuestionId(id);

        // Xóa các liên kết tới bảng questionAndAnswer
        questionAndAnswerService.deleteByQuestionId(id);

        // Lấy câu hỏi hiện có
        Question question = basicBeanRemote.find(Question.class,id);
        if (question == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy câu hỏi với ID đã cung cấp");
        }
        basicBeanRemote.remove(question);

        // xóa danh sách câu trả lời
        for (Answer answers : listAnswers) {
            if (answers.getAnswerId() != null) {
                Answer answer = basicBeanRemote.find(Answer.class,answers.getAnswerId());
                basicBeanRemote.remove(answer);
                } else {
                    return ResponseEntity.badRequest().body("Không tìm thấy câu trả lời với ID: " + answers.getAnswerId());
                }
            }
        return ResponseEntity.ok("Xóa thành công");
    }
}
