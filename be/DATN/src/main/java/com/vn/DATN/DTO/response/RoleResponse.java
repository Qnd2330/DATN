package com.vn.DATN.DTO.response;

import com.vn.DATN.entity.Role;
import com.vn.DATN.entity.Users;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class RoleResponse {
    Integer roleId;
    String roleName;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;

    public static RoleResponse fromRole(Role role){
        RoleResponse response = new RoleResponse();
        response.setRoleId(role.getRoleId());
        response.setRoleName(role.getRoleName());
        response.setCreateAt(role.getCreateAt());
        response.setUpdatedAt(role.getUpdatedAt());
        return response;
    }
}
