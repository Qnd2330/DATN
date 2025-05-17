package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Integer> {
    Optional<Users> findByUserName(String userName);
}
