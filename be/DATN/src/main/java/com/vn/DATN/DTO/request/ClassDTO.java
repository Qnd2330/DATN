package com.vn.DATN.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassDTO {
    private Integer classID;
    private String className;
    private Integer totalStudent;
    private String facultyName;
}
