package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.ClassAndCourseDTO;
import com.vn.DATN.entity.ClassCourse;

import java.util.List;

public interface ClassAndCourseService {
    List<ClassCourse> linkClassWithCourse (ClassAndCourseDTO dto);
    void deleteLinkClassWithCourse (ClassAndCourseDTO classId);
}
