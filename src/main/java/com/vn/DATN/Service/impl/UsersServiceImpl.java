package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.RegisterRequest;
import com.vn.DATN.Service.UsersService;
import com.vn.DATN.Service.repositories.RoleRepo;
import com.vn.DATN.Service.repositories.UserRepo;
import com.vn.DATN.entity.Role;
import com.vn.DATN.entity.Users;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    @Override
    public Page<Users> getAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @Override
    public Users save(RegisterRequest request) {
        Users user = new Users();
        user.setUserName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Users findByUserName(String userName) {
        Users users = userRepo.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("không tìm thấy người dùng"));
        return users;
    }

    @Override
    public Users addRoleToUser(String username, String roleName) {
        Users user = userRepo.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepo.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(role);

        return userRepo.save(user);
    }
}
