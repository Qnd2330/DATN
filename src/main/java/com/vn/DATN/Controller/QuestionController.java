package com.vn.DATN.Controller;

import com.vn.DATN.Common.BasicBeanRemote;
import com.vn.DATN.DTO.mapper.QuestionMapper;
import com.vn.DATN.DTO.request.QuestionDTO;
import com.vn.DATN.Service.QuestionAndAnswerService;
import com.vn.DATN.Service.QuestionService;
import com.vn.DATN.entity.Answer;
import com.vn.DATN.entity.Question;
import com.vn.DATN.entity.QuestionAnswer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final BasicBeanRemote basicBeanRemote;
    private final QuestionAndAnswerService questionAndAnswerService;
    private final QuestionService questionService;

    @GetMapping("/list")
    public List<Answer> list() {
        return basicBeanRemote.findAll(Answer.class);
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_QUESTION_ACCESS') or hasAuthority('CREATE_ACCESS')")
    public ResponseEntity<?> create(@RequestBody List<QuestionDTO> questionDTOList) {
        try {
            List<QuestionAnswer> created = questionAndAnswerService.create(questionDTOList);
            return ResponseEntity.ok(created);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('UPDATE_QUESTION_ACCESS') or hasAuthority('UPDATE_ACCESS')")
    public ResponseEntity<?> update(@PathVariable Integer id,@RequestBody QuestionDTO questionDTO) {
        try {
            questionDTO.setQuestionId(id);
            Question updated = questionService.edit(questionDTO);
            QuestionDTO result = QuestionMapper.INSTANCE.toDTO(updated);
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_QUESTION_ACCESS') or hasAuthority('DELETE_ACCESS')")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            boolean deleted = questionAndAnswerService.deleteByQuestionId(id);
            if (deleted) {
                return ResponseEntity.ok("Xóa câu hỏi và các câu trả lời thành công");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Xóa thất bại do một phần dữ liệu không thể xóa");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Lỗi: " + e.getMessage());
        }
    }
}
