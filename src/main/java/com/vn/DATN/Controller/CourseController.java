package com.vn.DATN.Controller;

import com.vn.DATN.DTO.request.CourseDTO;
import com.vn.DATN.DTO.response.CourseResponse;
import com.vn.DATN.DTO.response.PaginatedResponse;
import com.vn.DATN.Service.CourseService;
import com.vn.DATN.entity.Course;
import jakarta.validation.Valid;
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
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;

    @GetMapping
    @PreAuthorize("hasAuthority('GET_COURSE_ACCESS') or hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Course> coursePage = courseService.listCourse(pageable);

            List<CourseResponse> courseResponses = coursePage
                    .stream()
                    .map(CourseResponse::fromCourse)
                    .collect(Collectors.toList());

            PaginatedResponse<CourseResponse> response = new PaginatedResponse<>(
                    courseResponses,
                    coursePage.getNumber(),
                    coursePage.getTotalElements(),
                    coursePage.getTotalPages()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi lấy danh sách khóa học", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasAuthority('GET_COURSE_ACCESS') or hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getAll(){
        try{
            List<Course> courseList = courseService.getAll();
            List<CourseResponse> courseResponses = courseList
                    .stream()
                    .map(CourseResponse::fromCourse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(courseResponses);
        }catch (RuntimeException ex){
            log.error("Lỗi khi lấy danh sách khóa học", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_COURSE_ACCESS') or hasAuthority('CREATE_ACCESS')")
    public ResponseEntity<?> create(@Valid @RequestBody CourseDTO courseDTO) {
        try {
            Course created = courseService.create(courseDTO);
            CourseResponse courseResponse = CourseResponse.fromCourse(created);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Tạo khóa học thành công");
            response.put("data", courseResponse);

            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi tạo khóa học", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_COURSE_ACCESS') or hasAuthority('UPDATE_ACCESS')")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody CourseDTO courseDTO) {
        try {
            courseDTO.setCourseId(id);
            Course updated = courseService.edit(courseDTO);
            CourseResponse courseResponse = CourseResponse.fromCourse(updated);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Cập nhật khóa học thành công");
            response.put("data", courseResponse);

            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi cập nhật khóa học", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_COURSE_ACCESS') or hasAuthority('DELETE_ACCESS')")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            boolean deleted = courseService.delete(id);
            if (deleted) {
                return ResponseEntity.ok("Xóa khóa học thành công");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Xóa thất bại do một phần dữ liệu không thể xóa");
            }
        } catch (RuntimeException e) {
            log.error("Lỗi khi xóa khóa học", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
