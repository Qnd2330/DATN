package com.vn.DATN.Controller;

import com.vn.DATN.DTO.request.RoleDTO;
import com.vn.DATN.Service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(roleService.getAll());
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
