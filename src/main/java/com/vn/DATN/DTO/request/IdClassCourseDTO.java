package com.vn.DATN.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdClassCourseDTO {
    private Integer classId;
    private Integer courseId;
    private Integer newCourseId;
}
