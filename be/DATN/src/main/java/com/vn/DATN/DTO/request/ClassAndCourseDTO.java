package com.vn.DATN.DTO.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ClassAndCourseDTO {
    private Integer classId;
    private List<Integer> courseId;
}
