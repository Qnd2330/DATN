package com.vn.DATN.DTO.request;

import lombok.Data;

import java.util.List;

@Data
public class AddUsersToClassDTO {
    private Integer classId;
    private List<Integer> userIds;
}