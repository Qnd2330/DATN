package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepo extends JpaRepository<Class, Integer> {
    int countByKhoa_KhoaId(Integer khoaId);

    Class findByClassName (String className);
}
