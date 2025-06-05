package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepo extends JpaRepository<Permission, Integer> {
}
