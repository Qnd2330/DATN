package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.RoleDTO;
import com.vn.DATN.Service.RoleService;
import com.vn.DATN.Service.repositories.PermissionRepo;
import com.vn.DATN.Service.repositories.RoleRepo;
import com.vn.DATN.entity.Permission;
import com.vn.DATN.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepository;
    private final PermissionRepo permissionRepository;

    @Override
    public RoleDTO create(RoleDTO dto) {
        Role role = new Role();
        role.setRoleName(dto.getRoleName());

        Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(dto.getPermissionIds()));
        role.setPermissions(permissions);

        Role saved = roleRepository.save(role);
        dto.setId(saved.getRoleId());
        return dto;
    }

    @Override
    public RoleDTO update(Integer id, RoleDTO dto) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        role.setRoleName(dto.getRoleName());
        role.setPermissions(new HashSet<>(permissionRepository.findAllById(dto.getPermissionIds())));
        roleRepository.save(role);
        return dto;
    }

    @Override
    public void delete(Integer id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<RoleDTO> getAll() {
        return roleRepository.findAll().stream().map(role -> {
            RoleDTO dto = new RoleDTO();
            dto.setId(role.getRoleId());
            dto.setRoleName(role.getRoleName());
            dto.setPermissionIds((Set<Integer>) role.getPermissions().stream().map(Permission::getPermissionId).toList());
            return dto;
        }).collect(Collectors.toList());
    }
}
