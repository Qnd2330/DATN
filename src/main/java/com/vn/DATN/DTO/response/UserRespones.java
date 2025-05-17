package com.vn.DATN.DTO.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vn.DATN.entity.Role;
import com.vn.DATN.entity.Users;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserRespones {
    String token;

    private Integer userId;

    private String RoleName;

    private String userName;

    private String phoneNumber;

    private String email;

    private String password;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createAt;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime updatedAt;

    public static UserRespones fromUser (Users users, String token){
        UserRespones userRespones = new UserRespones();
        userRespones.setToken(token);
        userRespones.setUserId(users.getUserId());
        userRespones.setRoleName(users.getRoles().stream().findFirst().map(Role::getRoleName).orElse(null));
        userRespones.setUserName(users.getUsername());
        userRespones.setPhoneNumber(users.getPhoneNumber());
        userRespones.setEmail(users.getEmail());
        userRespones.setPassword(users.getPassword());
        userRespones.setCreateAt(users.getCreateAt());
        userRespones.setUpdatedAt(users.getUpdatedAt());
        return userRespones;
    }
}
