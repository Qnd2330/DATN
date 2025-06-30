package com.vn.DATN.Controller;

import com.vn.DATN.DTO.request.RoleDTO;
import com.vn.DATN.DTO.request.RoleWithPermissionsDTO;
import com.vn.DATN.Service.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            List<RoleWithPermissionsDTO> responses = roleService.getAll();
            return ResponseEntity.ok(responses);
        }catch (RuntimeException ex){
            log.error("Lỗi khi lấy danh sách chức vụ", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/{roleId}")
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getDetail(@PathVariable Integer roleId) {
        try {
            RoleWithPermissionsDTO responses = roleService.getRoleWithPermissions(roleId);
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
