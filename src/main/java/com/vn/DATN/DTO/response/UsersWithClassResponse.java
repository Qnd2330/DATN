package com.vn.DATN.DTO.response;

import com.vn.DATN.entity.Class;
import com.vn.DATN.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersWithClassResponse {
    private Class classes;
    private List<Users> users;
    private long totalElements;
    private int totalPages;
}
