package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.PermissionDTO;
import com.vn.DATN.DTO.request.RoleDTO;
import com.vn.DATN.DTO.request.RolePermissionDTO;
import com.vn.DATN.DTO.request.RoleWithPermissionsDTO;
import com.vn.DATN.Service.RoleService;
import com.vn.DATN.Service.repositories.PermissionRepo;
import com.vn.DATN.Service.repositories.RoleRepo;
import com.vn.DATN.entity.Permission;
import com.vn.DATN.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public List<RoleWithPermissionsDTO> getAll() {
        List<RolePermissionDTO> flatList = roleRepository.getAllRolePermissions();

        Map<Integer, RoleWithPermissionsDTO> groupedMap = new LinkedHashMap<>();

        for (RolePermissionDTO dto : flatList) {
            RoleWithPermissionsDTO role = groupedMap.computeIfAbsent(
                    dto.getRoleId(),
                    id -> {
                        RoleWithPermissionsDTO newRole = new RoleWithPermissionsDTO();
                        newRole.setRoleId(dto.getRoleId());
                        newRole.setRoleName(dto.getRoleName());
                        return newRole;
                    }
            );

            if (dto.getPermissionId() != null) {
                PermissionDTO perm = new PermissionDTO();
                perm.setId(dto.getPermissionId());
                perm.setPermissionName(dto.getPermissionName());
                role.getPermissions().add(perm);
            }
        }

        return new ArrayList<>(groupedMap.values());
    }

    @Override
    public RoleWithPermissionsDTO getRoleWithPermissions(Integer roleId) {
        List<RolePermissionDTO> result = roleRepository.getPermissionsByRoleId(roleId);

        if (result.isEmpty()) {
            throw new RuntimeException("Role not found with id = " + roleId);
        }

        RoleWithPermissionsDTO roleDTO = new RoleWithPermissionsDTO();
        roleDTO.setRoleId(result.get(0).getRoleId());
        roleDTO.setRoleName(result.get(0).getRoleName());

        List<PermissionDTO> permissions = result.stream()
                .filter(rp -> rp.getPermissionId() != null)
                .map(rp -> new PermissionDTO(rp.getPermissionId(), rp.getPermissionName()))
                .toList();

        roleDTO.setPermissions(permissions);
        return roleDTO;
    }
}
