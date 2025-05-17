package com.vn.DATN.DTO.response;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.vn.DATN.entity.Faculty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FacultyResponse {
    private Integer facultyId;
    private String facultyName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createAt;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime updatedAt;

    public static FacultyResponse fromFaculty(Faculty faculty) {
        FacultyResponse response = new FacultyResponse();
        response.setFacultyId(faculty.getFacultyId());
        response.setFacultyName(faculty.getFacultyName());
        response.setCreateAt(faculty.getCreateAt());
        response.setUpdatedAt(faculty.getUpdatedAt());
        return response;
    }
}