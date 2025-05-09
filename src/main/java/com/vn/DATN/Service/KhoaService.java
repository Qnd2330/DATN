package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.KhoaDTO;
import com.vn.DATN.entity.Khoa;

public interface KhoaService {
    Khoa create(KhoaDTO khoaDTO);

    Khoa edit(KhoaDTO khoaDTO);
    Khoa findByKhoaName(String khoaName);

    Khoa findById(Integer khoaId);
}
