package com.vn.DATN.DTO.response;

import com.vn.DATN.entity.Permission;
import lombok.Data;

@Data
public class PermissionResponse {
    Integer id;
    String permissionName;

    public static PermissionResponse from(Permission permission) {
        PermissionResponse response = new PermissionResponse();
        response.setId(permission.getPermissionId());
        response.setPermissionName(permission.getPermissionName());
        return response;
    }
}
