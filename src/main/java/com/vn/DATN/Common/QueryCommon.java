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

    public static final String GET_ALL_STUDENT = """
            SELECT u.*
            FROM users u
                JOIN role_user ru ON u.user_id = ru.user_id
                JOIN role r ON ru.role_id = r.role_id
            WHERE r.role_name = 'STUDENT'
              AND u.user_id NOT IN (SELECT user_id FROM user_class)
              AND u.deleted = false
            """;

    public static final String GET_SURVEY = """
            SELECT
                s.survey_id AS surveyId,
                s.title AS surveyTitle,
                s.description,
                c.course_name AS courseName,
                        
                q.question_id AS questionId,
                q.question_text AS questionText,
                q.type AS questionType,
                        
                a.answer_id AS answerId,
                a.content AS answerContent,
                a.point AS answerPoint
                        
            FROM survey s
            JOIN course c ON s.course_id = c.course_id
            JOIN survey_question sq ON s.survey_id = sq.survey_id
            JOIN question_answer qa ON sq.question_answer_id = qa.id
            JOIN question q ON qa.question_id = q.question_id
            JOIN answer a ON qa.answer_id = a.answer_id
                        
            WHERE s.survey_id = :surveyId
            ORDER BY q.question_id, a.answer_id;
            """;

    public static final String GET_LIST_SURVEY = """
            SELECT
                s.survey_id,
                s.title AS survey_title,
                c.course_name,
                u.user_name AS teacher_name,
                c.end_date AS deadline,
                CASE
                    WHEN sr.id IS NOT NULL THEN TRUE
                    ELSE FALSE
                END AS is_submitted
            FROM survey s
            JOIN course c ON s.course_id = c.course_id
            JOIN users u ON c.teach_id = u.user_id
            LEFT JOIN survey_result sr ON sr.survey_id = s.survey_id AND sr.user_id = :userId
            ORDER BY s.survey_id;
            """;
}
