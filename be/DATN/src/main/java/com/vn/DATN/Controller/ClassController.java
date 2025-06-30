package com.vn.DATN.Controller;

import com.vn.DATN.DTO.request.*;
import com.vn.DATN.DTO.response.ClassResponse;
import com.vn.DATN.DTO.response.PaginatedResponse;
import com.vn.DATN.DTO.response.UsersWithClassResponse;
import com.vn.DATN.Service.ClassAndCourseService;
import com.vn.DATN.Service.ClassService;
import com.vn.DATN.entity.Class;
import com.vn.DATN.entity.ClassCourse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/class")
@RequiredArgsConstructor
public class ClassController {
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    private final ClassService classService;
    private final ClassAndCourseService classAndCourseService;


    @GetMapping
    @PreAuthorize("hasAuthority('GET_CLASS_ACCESS') or hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Class> classPage = classService.list(pageable);

            List<ClassResponse> courseResponses = classPage
                    .stream()
                    .map(ClassResponse::fromClass)
                    .collect(Collectors.toList());

            PaginatedResponse<ClassResponse> response = new PaginatedResponse<>(
                    courseResponses,
                    classPage.getNumber(),
                    classPage.getTotalElements(),
                    classPage.getTotalPages()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi lấy danh sách lớp", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/{id}/detail")
    @PreAuthorize("hasAuthority('GET_CLASS_ACCESS') or hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getClassDetail(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(classService.getClassDetail(id));
        } catch (Exception ex) {
            log.error("Lỗi khi lấy danh sách lớp", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/{className}")
    @PreAuthorize("hasAuthority('GET_CLASS_ACCESS') or hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getAllStudent(@PathVariable String className,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            UsersWithClassResponse usersWithClass = classService.getall(className, pageable);
            return ResponseEntity.ok(usersWithClass);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi lấy danh sách lớp", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_CLASS_ACCESS') or hasAuthority('CREATE_ACCESS')")
    public ResponseEntity<?> create(@RequestBody ClassDTO classDTO) {
        try {
            Class created = classService.create(classDTO);
            ClassResponse classResponse = ClassResponse.fromClass(created);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Tạo lớp học thành công");
            response.put("data", classResponse);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_CLASS_ACCESS') or hasAuthority('UPDATE_ACCESS')")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ClassDTO classDTO) {
        try {
            classDTO.setClassID(id);
            Class updated = classService.edit(classDTO);
            ClassResponse classResponse = ClassResponse.fromClass(updated);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Cập nhật lớp học thành công");
            response.put("data", classResponse);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/add-users")
    @PreAuthorize("hasAuthority('CREATE_CLASS_ACCESS') or hasAuthority('CREATE_ACCESS')")
    public ResponseEntity<?> addUsersToClass(@RequestBody AddUsersToClassDTO dto) {
        try {
            classService.addUsersToClass(dto);
            return ResponseEntity.ok("Đã thêm học sinh vào lớp thành công.");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/addCourse")
    @PreAuthorize("hasAuthority('CREATE_CLASS_ACCESS') or hasAuthority('UPDATE_ACCESS')")
    public ResponseEntity<?> addCourse(@RequestBody ClassAndCourseDTO dto) {
        try {
            List<ClassCourse> created = classAndCourseService.linkClassWithCourse(dto);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Cập nhật khóa học thành công");
            response.put("data", created);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/course")
    @PreAuthorize("hasAuthority('DELETE_CLASS_ACCESS') or hasAuthority('DELETE_ACCESS')")
    public ResponseEntity<String> deleteLinkCourse(@RequestBody ClassAndCourseDTO dto) {
        try {
            classAndCourseService.deleteLinkClassWithCourse(dto);
            return ResponseEntity.ok("Xóa thành công");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/user")
    @PreAuthorize("hasAuthority('DELETE_CLASS_ACCESS') or hasAuthority('DELETE_ACCESS')")
    public ResponseEntity<String> deleteLinkUser(@RequestBody AddUsersToClassDTO dto) {
        try {
            classService.deleteLinkUser(dto);
            return ResponseEntity.ok("Xóa thành công");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_CLASS_ACCESS') or hasAuthority('DELETE_ACCESS')")
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
