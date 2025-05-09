package com.vn.DATN.Service;

import com.vn.DATN.entity.ClassCourse;

public interface ClassAndCourseService {
    ClassCourse linkClassWithCourse (Integer classId, Integer courseId);

    ClassCourse editLinkClassWithCourse (Integer classID, Integer courseId, Integer newCourseId);

    ClassCourse deleteLinkClassWithCourse (Integer classId);
}
