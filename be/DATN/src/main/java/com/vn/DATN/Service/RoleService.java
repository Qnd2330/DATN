package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.RoleDTO;
import com.vn.DATN.DTO.request.RoleWithPermissionsDTO;

import java.util.List;

public interface RoleService {
    List<RoleWithPermissionsDTO> getAll();

    RoleWithPermissionsDTO getRoleWithPermissions(Integer roleId);

    RoleDTO create(RoleDTO roleDTO);

    RoleDTO update(Integer id, RoleDTO roleDTO);

    void delete(Integer id);
}
