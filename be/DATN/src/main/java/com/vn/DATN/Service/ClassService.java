package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.AddUsersToClassDTO;
import com.vn.DATN.DTO.request.ClassDTO;
import com.vn.DATN.DTO.response.ClassDetailResponse;
import com.vn.DATN.DTO.response.UsersWithClassResponse;
import com.vn.DATN.entity.Class;
import com.vn.DATN.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClassService {
    Page<Class> list(Pageable pageable);

    ClassDetailResponse getClassDetail(Integer classId);

    UsersWithClassResponse getall(String className, Pageable pageable);
    Class create(ClassDTO classDTO);

    Class edit(ClassDTO classDTO);
    int countByKhoa_KhoaId(Integer khoaId);

    List<Users> getAll();

    void addUsersToClass(AddUsersToClassDTO dto);

    Class findById(Integer classId);

    Class findByName(String className);

    void deleteLinkUser(AddUsersToClassDTO dto);

    boolean delete(Integer classId);
}
