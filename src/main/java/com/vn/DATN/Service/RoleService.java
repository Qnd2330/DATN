package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.RoleDTO;

import java.util.List;

public interface RoleService {
    List<RoleDTO> getAll();

    RoleDTO create(RoleDTO roleDTO);

    RoleDTO update(Integer id, RoleDTO roleDTO);

    void delete(Integer id);
}
