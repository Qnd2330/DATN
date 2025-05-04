package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users, Integer> {
    Users findByUserName (String userName);
}
