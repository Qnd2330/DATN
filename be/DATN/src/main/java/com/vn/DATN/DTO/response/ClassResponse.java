package com.vn.DATN.DTO.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vn.DATN.entity.Class;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ClassResponse {
    private Integer classId;
    private String className;
    private Integer totalStudent;
    private String facultyName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createAt;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime updatedAt;

    public static ClassResponse fromClass(Class classs) {
        ClassResponse response = new ClassResponse();
        response.setClassId(classs.getClassId());
        response.setClassName(classs.getClassName());
        response.setTotalStudent(classs.getTotalStudent());
        response.setCreateAt(classs.getCreateAt());
        response.setUpdatedAt(classs.getUpdatedAt());

        if (classs.getFaculty() != null) {
            response.setFacultyName(classs.getFaculty().getFacultyName());
        } else {
            response.setFacultyName(null);
        }

        return response;
    }
}
