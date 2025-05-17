package com.vn.DATN.Service;


import com.vn.DATN.DTO.request.RegisterRequest;
import com.vn.DATN.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsersService {
    Page<Users> getAll (Pageable pageable);
    Users save(RegisterRequest request);
    Users findByUserName (String userName);
    Users addRoleToUser(String username, String roleName);
}
