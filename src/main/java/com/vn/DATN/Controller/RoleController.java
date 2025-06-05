package com.vn.DATN.Controller;

import com.vn.DATN.DTO.request.RoleDTO;
import com.vn.DATN.DTO.response.CourseResponse;
import com.vn.DATN.DTO.response.RoleResponse;
import com.vn.DATN.Service.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getAll() {
        try {
            List<RoleResponse> responses = roleService.getAll()
                    .stream()
                    .map(RoleResponse::fromRole)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        }catch (RuntimeException ex){
            log.error("Lỗi khi lấy danh sách chức vụ", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ACCESS')")
    public ResponseEntity<?> create(@RequestBody RoleDTO dto) {
        try {
            return ResponseEntity.ok(roleService.create(dto));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ACCESS')")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody RoleDTO dto) {
        try {
            return ResponseEntity.ok(roleService.update(id, dto));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ACCESS')")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try{
        roleService.delete(id);
        return ResponseEntity.ok("Xóa thành công");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
