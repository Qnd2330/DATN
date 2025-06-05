package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.RoleDTO;
import com.vn.DATN.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAll();

    RoleDTO create(RoleDTO roleDTO);

    RoleDTO update(Integer id, RoleDTO roleDTO);

    void delete(Integer id);
}
