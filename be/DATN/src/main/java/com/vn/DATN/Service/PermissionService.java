package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.PermissionDTO;
import com.vn.DATN.entity.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> getAll();
    Permission create(PermissionDTO dto);
    Permission update(Integer id, PermissionDTO dto);
    void delete(Integer id);
}
