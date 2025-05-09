package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.ClassDTO;
import com.vn.DATN.entity.Class;

public interface ClassService {
    Class create(ClassDTO classDTO);

    Class edit(ClassDTO classDTO);
    int countByKhoa_KhoaId(Integer khoaId);

    Class findById(Integer classId);

    Class findByName(String className);

    boolean delete(Integer classId);
}
