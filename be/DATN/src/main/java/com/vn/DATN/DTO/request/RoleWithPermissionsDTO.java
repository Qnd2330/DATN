package com.vn.DATN.DTO.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class RoleWithPermissionsDTO {
    private Integer roleId;
    private String roleName;
    private List<PermissionDTO> permissions = new ArrayList<>();

}
