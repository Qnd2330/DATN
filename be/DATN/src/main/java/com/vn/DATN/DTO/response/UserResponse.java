package com.vn.DATN.DTO.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vn.DATN.entity.Role;
import com.vn.DATN.entity.Users;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserResponse {
    String token;

    private Integer userId;

    private String RoleName;

    private String userName;

    private int phoneNumber;

    private String email;

    private String password;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createAt;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime updatedAt;

    public static UserResponse fromUserWithToken(Users users, String token){
        UserResponse userResponse = new UserResponse();
        userResponse.setToken(token);
        userResponse.setUserId(users.getUserId());
        userResponse.setRoleName(users.getRoles().stream().findFirst().map(Role::getRoleName).orElse(null));
        userResponse.setUserName(users.getUserNamee());
        userResponse.setPhoneNumber(users.getPhoneNumber());
        userResponse.setEmail(users.getEmail());
        userResponse.setPassword(users.getPassword());
        userResponse.setCreateAt(users.getCreateAt());
        userResponse.setUpdatedAt(users.getUpdatedAt());
        return userResponse;
    }

    public static UserResponse fromUser(Users users){
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(users.getUserId());
        userResponse.setRoleName(users.getRoles().stream().findFirst().map(Role::getRoleName).orElse(null));
        userResponse.setUserName(users.getUserNamee());
        userResponse.setPhoneNumber(users.getPhoneNumber());
        userResponse.setEmail(users.getEmail());
        userResponse.setPassword(users.getPassword());
        userResponse.setCreateAt(users.getCreateAt());
        userResponse.setUpdatedAt(users.getUpdatedAt());
        return userResponse;
    }
}
