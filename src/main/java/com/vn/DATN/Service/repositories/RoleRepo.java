package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role,Integer> {
    Optional<Role> findByRoleName (String roleName);

}
