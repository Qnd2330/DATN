package com.vn.DATN.Controller;

import com.vn.DATN.Common.BasicBeanRemote;
import com.vn.DATN.DTO.request.QuestionDTO;
import com.vn.DATN.DTO.request.AnswerDTO;
import com.vn.DATN.entity.Answer;
import com.vn.DATN.entity.Question;
import com.vn.DATN.Service.AnswerService;
import com.vn.DATN.Service.QuestionAndAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerControler {
    private final BasicBeanRemote basicBeanRemote;
    private final AnswerService answerService;

    @GetMapping("/list")
    public List<Answer> list() {
        return basicBeanRemote.findAll(Answer.class);
    }

    //     Đấy là ví dụ hàm craete answe, question tương tự.
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AnswerDTO answerDTO) {
        answerService.create(answerDTO);
        return ResponseEntity.ok(answerDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody AnswerDTO answerDTO) {
        try {
            answerDTO.setAnswerId(id);
            Answer updated = answerService.edit(answerDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy câu trả lời với ID: " + id);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            boolean deleted = answerService.deleteById(id);
            if (deleted) {
                return ResponseEntity.ok("Xóa thành công");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể xóa câu trả lời");
            }
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy câu trả lời với ID: " + id);
        }
    }
}
