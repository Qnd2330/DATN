package com.vn.DATN.Controller;

import com.vn.DATN.DTO.request.PermissionDTO;
import com.vn.DATN.DTO.response.PermissionResponse;
import com.vn.DATN.Service.PermissionService;
import com.vn.DATN.entity.Permission;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private static final Logger log = LoggerFactory.getLogger(PermissionController.class);
    private final PermissionService permissionService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getAll() {
        try {
            List<Permission> permissions = permissionService.getAll();
            List<PermissionResponse> response = permissions
                    .stream()
                    .map(PermissionResponse::from)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi lấy danh sách quyền", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ACCESS')")
    public ResponseEntity<?> create(@RequestBody PermissionDTO dto) {
        try {
            Permission created = permissionService.create(dto);
            PermissionResponse response = PermissionResponse.from(created);

            Map<String, Object> result = new HashMap<>();
            result.put("message", "Tạo quyền thành công");
            result.put("data", response);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi tạo quyền", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ACCESS')")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody PermissionDTO dto) {
        try {
            Permission updated = permissionService.update(id, dto);
            PermissionResponse response = PermissionResponse.from(updated);

            Map<String, Object> result = new HashMap<>();
            result.put("message", "Cập nhật quyền thành công");
            result.put("data", response);

            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi cập nhật quyền", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_ACCESS')")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            permissionService.delete(id);
            return ResponseEntity.ok("Xóa quyền thành công");
        } catch (RuntimeException ex) {
            log.error("Lỗi khi xóa quyền", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
