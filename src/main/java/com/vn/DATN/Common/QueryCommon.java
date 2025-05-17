package com.vn.DATN.Common;

public class QueryCommon {
    public static final String CLASS_DETAIL = """
        SELECT
            c.class_id AS classId,
            c.class_name AS className,
            c.total_student AS totalStudent,
            k.faculty_name AS facultyName,

            u.user_id AS userId,
            u.user_name AS userName,
            u.phone_number as phone,
            u.email as email,

            cs.course_id AS courseId,
            cs.course_name AS courseName,
            cs.teach_id as teacherId
           

        FROM class c
        JOIN faculty k ON c.faculty_id = k.faculty_id

        LEFT JOIN user_class uc ON c.class_id = uc.class_id
        LEFT JOIN users u ON uc.user_id = u.user_id

        LEFT JOIN class_course cc ON c.class_id = cc.class_id
        LEFT JOIN course cs ON cc.course_id = cs.course_id

        WHERE c.class_id = :classId
    """;
}
