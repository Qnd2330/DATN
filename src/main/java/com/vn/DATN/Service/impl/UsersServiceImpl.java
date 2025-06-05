package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.RegisterRequest;
import com.vn.DATN.DTO.request.StudentDTO;
import com.vn.DATN.DTO.request.UserDTO;
import com.vn.DATN.Service.UsersService;
import com.vn.DATN.Service.repositories.RoleRepo;
import com.vn.DATN.Service.repositories.UserRepo;
import com.vn.DATN.entity.Role;
import com.vn.DATN.entity.Survey;
import com.vn.DATN.entity.Users;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    @Override
    public Page<Users> list(Pageable pageable) {
        return userRepo.findAllNotDeleted(pageable);
    }

    @Override
    public List<Users> getAll() {
        return userRepo.findUnassignedStudents();
    }

    @Override
    @Transactional
    public List<Users> addMultipleStudents(List<StudentDTO> studentDTOList) {
        // Lấy role STUDENT 1 lần
        Role studentRole = roleRepo.findByRoleName("STUDENT")
                .orElseThrow(() -> new RuntimeException("Role STUDENT not found"));

        List<Users> usersToSave = new ArrayList<>();

        for (StudentDTO dto : studentDTOList) {
            // Bỏ qua nếu user đã tồn tại
            if (userRepo.existsById(dto.getStudentId())) {
                continue;
            }

            Users user = new Users();
            user.setUserId(dto.getStudentId());
            user.setUserName(dto.getStudentName());
            user.setPassword(passwordEncoder.encode(String.valueOf(dto.getStudentId()))); // Mật khẩu = mã sinh viên
            user.getRoles().add(studentRole); // Gán role STUDENT

            usersToSave.add(user);
        }

        return userRepo.saveAll(usersToSave);
    }

    @Override
    public Users create(RegisterRequest request) {
        Users users = new Users();
        users.setUserId(request.getUserId());
        users.setPassword(request.getPassword());
        users.setUserName(request.getUserId().toString());
        return userRepo.save(users);
    }

    @Override
    public Users edit(UserDTO request) {
        Users users = userRepo.findById(request.getUserId()).orElseThrow(() ->new RuntimeException("Không tìm thấy ngươi dùng"));
        users.setUserId(request.getUserId());
        users.setUserName(request.getUserName());
        users.setEmail(request.getEmail());
        users.setPhoneNumber(request.getPhoneNumber());
        users.setPassword(request.getPassword());
        return userRepo.saveAndFlush(users);
    }

    @Override
    public void delete(Integer userId) {
        Users users = userRepo.findById(userId).orElseThrow(() ->new RuntimeException("Không tìm thấy ngươi dùng"));
        users.setDeleted(true);
        userRepo.saveAndFlush(users);
    }

    @Override
    public Users findByUserName(String userName) {
        Users users = userRepo.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("không tìm thấy người dùng"));
        return users;
    }

    @Override
    public Users addRoleToUser(Integer userId, String roleName) {
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepo.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(role);

        return userRepo.save(user);
    }
}
