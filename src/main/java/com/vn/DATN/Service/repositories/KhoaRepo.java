package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.Khoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KhoaRepo extends JpaRepository<Khoa,Integer> {
    Khoa findByKhoaName (String khoaName);
}
