package com.vn.DATN.Controller;

import com.vn.DATN.DTO.mapper.ClassMapper;
import com.vn.DATN.DTO.request.ClassCourseDTO;
import com.vn.DATN.DTO.request.ClassDTO;
import com.vn.DATN.DTO.request.IdClassCourseDTO;
import com.vn.DATN.Service.ClassAndCourseService;
import com.vn.DATN.Service.ClassService;
import com.vn.DATN.entity.Class;
import com.vn.DATN.entity.ClassCourse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/class")
@RequiredArgsConstructor
public class ClassController {
    private final ClassService classService;

    private final ClassAndCourseService classAndCourseService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ClassDTO classDTO) {
        try {
            Class created = classService.create(classDTO);
            ClassDTO result = ClassMapper.INSTANCE.toDTO(created);
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ClassDTO classDTO) {
        try {
            classDTO.setClassID(id);
            Class updated = classService.edit(classDTO);
            ClassDTO result = ClassMapper.INSTANCE.toDTO(updated);
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/addCourse")
    public ResponseEntity<?> addCourse(@RequestBody ClassCourseDTO classCourseDTO) {
        try {
            ClassCourse created = classAndCourseService.linkClassWithCourse(classCourseDTO.getClassId(), classCourseDTO.getCourseId());
            return ResponseEntity.ok("Đã thêm môn học thành công");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/editCourse")
    public ResponseEntity<?> editCourse(@RequestBody IdClassCourseDTO id) {
        try {
            ClassCourse updated = classAndCourseService.editLinkClassWithCourse(id.getClassId(), id.getCourseId(), id.getNewCourseId());
            return ResponseEntity.ok("Cập nhật thành công");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            boolean deleted = classService.delete(id);
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
