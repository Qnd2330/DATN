package com.vn.DATN.Service.repositories;

import com.vn.DATN.Common.QueryCommon;
import com.vn.DATN.DTO.request.RolePermissionDTO;
import com.vn.DATN.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role,Integer> {
    Optional<Role> findByRoleName (String roleName);

    @Query(value = QueryCommon.GET_ALL_ROLE_PERMISSIONS, nativeQuery = true)
    List<RolePermissionDTO> getAllRolePermissions();

    @Query(value = QueryCommon.GET_DETAIL_ROLE_PERMISSIONS, nativeQuery = true)
    List<RolePermissionDTO> getPermissionsByRoleId(@Param("roleId") Integer roleId);
}
