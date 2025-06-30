package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.RegisterRequest;
import com.vn.DATN.DTO.request.StudentDTO;
import com.vn.DATN.DTO.request.UserDTO;
import com.vn.DATN.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UsersService {
    Page<Users> list(Pageable pageable);

    List<Users> getAll();

    List<Users> addMultipleStudents(List<StudentDTO> studentDTOList);

    List<Users> importStudentsFromExcel(MultipartFile file);

    Users create(RegisterRequest request);

    Users edit(UserDTO request);

    void changePassword(Users users, String newPassword);

    void delete(Integer userId);

    Users findById (Integer userId);

    Users findByUserName (String userName);

    Users addRoleToUser(Integer userId, String roleName);
}
