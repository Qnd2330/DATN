package com.vn.DATN.Controller;

import com.vn.DATN.DTO.request.FacultyDTO;
import com.vn.DATN.DTO.response.FacultyResponse;
import com.vn.DATN.DTO.response.PaginatedResponse;
import com.vn.DATN.Service.FacultyService;
import com.vn.DATN.entity.Faculty;
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
@RequestMapping("/faculty")
@RequiredArgsConstructor
public class FacultyController {
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    private final FacultyService facultyService;

    @GetMapping
    @PreAuthorize("hasAuthority('GET_FACULTY_ACCESS') or hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Faculty> facultyPage = facultyService.list(pageable);

            List<FacultyResponse> facultyResponses = facultyPage
                    .stream()
                    .map(FacultyResponse::fromFaculty)
                    .collect(Collectors.toList());

            PaginatedResponse<FacultyResponse> response = new PaginatedResponse<>(
                    facultyResponses,
                    facultyPage.getNumber(),
                    facultyPage.getTotalElements(),
                    facultyPage.getTotalPages()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi lấy danh sách khoa", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    @GetMapping("/get-all")
    @PreAuthorize("hasAuthority('GET_FACULTY_ACCESS') or hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getAll(){
        try {
            List<Faculty> response = facultyService.getAll();
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi lấy danh sách khoa", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_FACULTY_ACCESS') or hasAuthority('CREATE_ACCESS')")
    public ResponseEntity<?> create(@RequestBody FacultyDTO facultyDTO) {
        try {
            Faculty created = facultyService.create(facultyDTO);
            FacultyResponse responseData = FacultyResponse.fromFaculty(created);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Tạo khoa thành công");
            response.put("data", responseData);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi tạo khoa", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_FACULTY_ACCESS') or hasAuthority('UPDATE_ACCESS')")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody FacultyDTO facultyDTO) {
        try {
            facultyDTO.setFacultyId(id);
            Faculty updated = facultyService.edit(facultyDTO);
            FacultyResponse responseData = FacultyResponse.fromFaculty(updated);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Cập nhật khoa thành công");
            response.put("data", responseData);

            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi cập nhật khoa", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('DELETE_FACULTY_ACCESS') or hasAuthority('DELETE_ACCESS')")
//    public ResponseEntity<?> delete(@PathVariable Integer id) {
//        try {
//            boolean deleted = facultyService.(id);
//            if (deleted) {
//                return ResponseEntity.ok("Xóa khoa thành công");
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body("Xóa thất bại do một phần dữ liệu không thể xóa");
//            }
//        } catch (RuntimeException ex) {
//            log.error("Lỗi khi xóa khoa", ex);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
//        }
//    }
}
