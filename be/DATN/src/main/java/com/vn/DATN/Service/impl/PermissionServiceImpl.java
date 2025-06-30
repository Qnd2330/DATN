package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.PermissionDTO;
import com.vn.DATN.Service.PermissionService;
import com.vn.DATN.Service.repositories.PermissionRepo;
import com.vn.DATN.entity.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepo permissionRepo;

    @Override
    public Permission create(PermissionDTO dto) {
        Permission permission = new Permission();
        permission.setPermissionName(dto.getPermissionName());
        return permissionRepo.save(permission);
    }

    @Override
    public Permission update(Integer id, PermissionDTO dto) {
        Permission permission = permissionRepo.findById(id).orElseThrow(() -> new RuntimeException("Không tim thấy quyền"));
        permission.setPermissionName(dto.getPermissionName());
        return permissionRepo.save(permission);
    }

    @Override
    public void delete(Integer id) {
        permissionRepo.deleteById(id);
    }

    @Override
    public List<Permission> getAll() {
        return permissionRepo.findAll(Sort.by(Sort.Direction.ASC, "permissionId"));
    }
}