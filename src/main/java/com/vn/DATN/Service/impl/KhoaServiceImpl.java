package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.KhoaDTO;
import com.vn.DATN.Service.KhoaService;
import com.vn.DATN.Service.repositories.KhoaRepo;
import com.vn.DATN.entity.Khoa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KhoaServiceImpl implements KhoaService {
    private final KhoaRepo khoaRepo;

    @Override
    public Khoa create(KhoaDTO khoaDTO) {
        Khoa khoa = Khoa.builder()
                .khoaName(khoaDTO.getKhoaName())
                .build();
        return khoaRepo.save(khoa);
    }

    @Override
    public Khoa edit(KhoaDTO khoaDTO) {
        Khoa khoa = findById(khoaDTO.getKhoaId());
        if(khoa == null){
            throw new RuntimeException("Không tìm thấy Khoa");
        }
        khoa.setKhoaName(khoaDTO.getKhoaName());
        return khoaRepo.saveAndFlush(khoa);
    }

    @Override
    public Khoa findByKhoaName(String khoaName) {
        Khoa khoa = khoaRepo.findByKhoaName(khoaName);
        if(khoa == null) {
            return null;
        }
        return khoa;
    }

    @Override
    public Khoa findById(Integer khoaId) {
        return khoaRepo.findById(khoaId).orElse(null);
    }
}
