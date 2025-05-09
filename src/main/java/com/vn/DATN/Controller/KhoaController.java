package com.vn.DATN.Controller;

import com.vn.DATN.DTO.mapper.KhoaMapper;
import com.vn.DATN.DTO.request.KhoaDTO;
import com.vn.DATN.Service.KhoaService;
import com.vn.DATN.entity.Khoa;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/khoa")
@RequiredArgsConstructor
public class KhoaController {
    private final KhoaService khoaService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody KhoaDTO khoaDTO) {
        try {
            Khoa created = khoaService.create(khoaDTO);
            KhoaDTO result = KhoaMapper.INSTANCE.toDTO(created);
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody KhoaDTO khoaDTO) {
        try {
            khoaDTO.setKhoaId(id);
            Khoa updated = khoaService.edit(khoaDTO);
            KhoaDTO result = KhoaMapper.INSTANCE.toDTO(updated);
            return ResponseEntity.ok(result);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
