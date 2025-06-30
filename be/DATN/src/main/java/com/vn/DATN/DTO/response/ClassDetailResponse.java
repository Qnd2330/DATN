package com.vn.DATN.DTO.response;

import com.vn.DATN.DTO.request.CourseDTO;
import com.vn.DATN.DTO.request.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassDetailResponse {
    private Integer classId;
    private String className;
    private Integer totalStudent;
    private String facultyName;
    private List<CourseDTO> courses = new ArrayList<>();
    private List<StudentDTO> students = new ArrayList<>();
}