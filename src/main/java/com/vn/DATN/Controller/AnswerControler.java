package com.vn.DATN.Controller;

import com.vn.DATN.DTO.request.AnswerDTO;
import com.vn.DATN.entity.Answer;
import com.vn.DATN.Service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerControler {
    private final AnswerService answerService;

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ANSWER_ACCESS') or hasAuthority('UPDATE_ACCESS')")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody AnswerDTO answerDTO) {
        try {
            answerDTO.setAnswerId(id);
            Answer updated = answerService.edit(answerDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy câu trả lời với ID: " + id);
        }
    }
}
