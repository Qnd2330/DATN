package com.vn.DATN.Controller;

import com.vn.DATN.DTO.mapper.CourseMapper;
import com.vn.DATN.DTO.request.CourseDTO;
import com.vn.DATN.Service.CourseService;
import com.vn.DATN.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CourseDTO courseDTO) {
        try {
            Course created = courseService.create(courseDTO);
            CourseDTO result = CourseMapper.INSTANCE.toDTO(created);
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody CourseDTO courseDTO) {
        try {
            courseDTO.setCourseId(id);
            Course updated = courseService.edit(courseDTO);
            CourseDTO result = CourseMapper.INSTANCE.toDTO(updated);
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            boolean deleted = courseService.delete(id);
            if (deleted) {
                return ResponseEntity.ok("Xóa thành công");
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
