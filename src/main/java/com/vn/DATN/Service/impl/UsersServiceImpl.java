package com.vn.DATN.Service.impl;

import com.vn.DATN.Service.UsersService;
import com.vn.DATN.Service.repositories.UserRepo;
import com.vn.DATN.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UserRepo userRepo;
    @Override
    public Users findByUserName(String userName) {
        Users users = userRepo.findByUserName(userName);
        if(users == null){
            return null;
        }
        return users;
    }
}
