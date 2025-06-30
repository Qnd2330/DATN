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
            WHERE r.role_name = 'STUDENT' or r.role_name = 'MONITOR'
              AND u.user_id NOT IN (SELECT user_id FROM user_class)
              AND u.deleted = false
            """;

    public static final String GET_SURVEY = """
            SELECT
                s.survey_id AS surveyId,
                s.title AS surveyTitle,
                s.description AS description,
                s.deadline as deadline,
                c.course_name AS courseName,
                    
                q.question_id AS questionId,
                q.question_text AS questionText,
                q.type AS questionType,
                    
                a.answer_id AS answerId,
                a.content AS answerContent
                    
            FROM survey s
            LEFT JOIN survey_question sq ON s.survey_id = sq.survey_id
            LEFT JOIN question_answer qa ON sq.question_answer_id = qa.id
            LEFT JOIN question q ON qa.question_id = q.question_id
            LEFT JOIN answer a ON qa.answer_id = a.answer_id
            LEFT JOIN course_survey cs ON s.survey_id = cs.survey_id
            LEFT JOIN course c ON cs.course_id = c.course_id
            WHERE s.survey_id = :surveyId
            ORDER BY q.question_id, a.answer_id;
            """;

    public static final String GET_LIST_SURVEY = """
            SELECT DISTINCT
                s.survey_id AS surveyId,
                s.title AS surveyTitle,
                s.deadline AS deadline
            FROM survey s
            LEFT JOIN course_survey cs ON s.survey_id = cs.survey_id
            LEFT JOIN course c ON cs.course_id = c.course_id
            LEFT JOIN class_course cc ON c.course_id = cc.course_id
            LEFT JOIN class cl ON cc.class_id = cl.class_id
            LEFT JOIN user_class uc ON cl.class_id = uc.class_id
            LEFT JOIN survey_result sr ON s.survey_id = sr.survey_id AND sr.user_id = :userId
            WHERE (uc.user_id = :userId OR cs.survey_id IS NULL)
              AND sr.id IS NULL;
            """;

    public static final String GET_LIST_SURVEY_RESULT_OF_MONITOR = """
            SELECT DISTINCT
                 sr.id as surveyResultId,
                 s.survey_id as surveyId,
                 sr.user_id as userId,
                 sr.evaluator_id as evaluatorId,
                 s.title AS surveyTitle,
                 u_target.user_name AS studentName,
                 sr.create_at AS createAt,
                 
                 CASE WHEN r.role_name = 'MONITOR' THEN TRUE ELSE FALSE END AS isMonitor
                  
                        
            FROM survey_result sr
                        
            JOIN survey s ON s.survey_id = sr.survey_id
            JOIN users u_target ON sr.user_id = u_target.user_id
            JOIN user_class uc_target ON u_target.user_id = uc_target.user_id
            JOIN user_class uc_monitor ON uc_monitor.user_id = :monitorId
            JOIN class c ON uc_target.class_id = c.class_id AND uc_monitor.class_id = c.class_id
                        
            LEFT JOIN role_user ru ON ru.user_id = sr.user_id
            LEFT JOIN role r ON r.role_id = ru.role_id
                        
            -- Chỉ lấy các bản CHƯA được đánh giá
            WHERE sr.evaluator_id IS NULL
              and sr.user_id != :monitorId
                        
              -- Loại bỏ nếu đã tồn tại bản ghi cùng survey_id + user_id nhưng đã có evaluator_id
              AND NOT EXISTS (
                  SELECT 1
                  FROM survey_result sr2
                  WHERE sr2.user_id = sr.user_id
                    AND sr2.survey_id = sr.survey_id
                    AND sr2.evaluator_id IS NOT NULL
              )
                        
            ORDER BY sr.create_at DESC
            """;

    public static final String GET_LIST_SURVEY_RESULT_OF_TEACHER = """
            SELECT
                sr.id as surveyResultId,
                s.survey_id as surveyId,
                sr_student.id AS srStudentId,
                sr.user_id as userId,
                s.title AS surveyTitle,
                u_target.user_name AS studentName,
                sr.evaluator_id as evaluatorId,
                evaluator.user_name as evaluatorName,
                sr.create_at AS createAt,
                
                CASE WHEN r.role_name = 'MONITOR' THEN TRUE ELSE FALSE END AS isMonitor
                
            FROM survey_result sr
                        
            JOIN users u_target ON sr.user_id = u_target.user_id
                   
            LEFT JOIN users evaluator ON sr.evaluator_id = evaluator.user_id
                        
            JOIN survey s ON s.survey_id = sr.survey_id
                        
            JOIN user_class uc ON sr.user_id = uc.user_id
            JOIN class c ON uc.class_id = c.class_id
                        
            LEFT JOIN faculty_user fu ON fu.faculty_id = c.faculty_id
                        
            LEFT JOIN course_survey cs ON cs.survey_id = s.survey_id
            LEFT JOIN course co ON co.course_id = cs.course_id
                        
            LEFT JOIN survey_result sr_student
                            ON sr_student.user_id = sr.user_id
                            AND sr_student.evaluator_id IS NULL
                            AND sr_student.survey_id = sr.survey_id
                            
            LEFT JOIN role_user ru ON ru.user_id = sr.user_id
            LEFT JOIN role r ON r.role_id = ru.role_id
                        
            -- CHỈ lấy các bản ĐÃ được MONITOR đánh giá
            WHERE sr.evaluator_id != :teacherId
                and sr.evaluator_id is not null
                        
                        
            -- Và người TEACHER hiện tại có quyền truy cập
            AND NOT EXISTS (
                 SELECT 1
                 FROM survey_result sr2
                 WHERE sr2.user_id = sr.user_id
                   AND sr2.survey_id = sr.survey_id
                   AND sr2.evaluator_id = 2110900010
            )
                       
            -- TEACHER có quyền đánh giá
            AND (
                 co.teach_id = 2110900010
                 OR fu.user_id = 2110900010
            )
            """;
    public static final String GET_DETAIL_SURVEY_RESULT = """
            SELECT
                sr.id AS survey_result_id,
               
                u.user_id,
                u.user_name,
                u.email,
                        
                sr.evaluator_id,
                evaluator.user_name AS evaluator_name,
                        
                s.survey_id,
                s.title,
                s.description,
                s.deadline,
                        
                c.course_id,
                c.course_name,
                        
                q.question_id,
                q.question_text,
                q.type,
                        
                a.answer_id,
                a.content
                        
            FROM survey_result sr
                        
            -- Join lấy người được đánh giá
            JOIN users u ON sr.user_id = u.user_id
                        
            -- Join lấy người đánh giá (có thể null)
            LEFT JOIN users evaluator ON sr.evaluator_id = evaluator.user_id
                        
            -- Join lấy thông tin survey
            JOIN survey s ON sr.survey_id = s.survey_id
                        
            -- Join qua course_survey để lấy course (nếu có)
            LEFT JOIN course_survey cs ON cs.survey_id = s.survey_id
            LEFT JOIN course c ON cs.course_id = c.course_id
                        
            -- Join lấy câu trả lời
            JOIN user_answers ua ON ua.survey_result_id = sr.id
            JOIN question_answer qa ON qa.id = ua.question_answer_id
            JOIN question q ON qa.question_id = q.question_id
            JOIN answer a ON qa.answer_id = a.answer_id
                        
            WHERE sr.id = :surveyResultId
                        
            ORDER BY q.question_id;
            """;

    public static final String GET_DONE_SURVEY_RESULT_FOR_STUDENT = """
            SELECT
                 sr.id AS surveyResultId,
                 sr.survey_id AS surveyId,
                 s.title AS surveyTitle,

                 sr.create_at AS createAt,
                 sr.updated_at AS updatedAt,
                    
                 c.course_id AS courseId,
                 c.course_name AS courseName
                    
            FROM survey_result sr
            JOIN users u ON sr.user_id = u.user_id
            JOIN survey s ON sr.survey_id = s.survey_id
            LEFT JOIN course_survey cs ON cs.survey_id = s.survey_id
            LEFT JOIN course c ON cs.course_id = c.course_id
            LEFT JOIN users evaluator ON sr.evaluator_id = evaluator.user_id
                    
            WHERE sr.user_id = :userId
              AND sr.evaluator_id IS NULL
            """;

    public static final String GET_DONE_SURVEY_RESULT_FOR_MONITOR_AND_TEACHER = """
            SELECT
                sr.id AS survey_result_id,
                sr.survey_id,
                s.title AS survey_title,
                       
                sr.user_id,
                u.user_name AS student_name,
                       
                sr.evaluator_id,
                evaluator.user_name AS evaluator_name,
                       
                sr.create_at,
                sr.updated_at,
                       
                c.course_id,
                c.course_name
                       
            FROM survey_result sr
            JOIN users u ON sr.user_id = u.user_id
            JOIN survey s ON sr.survey_id = s.survey_id
            LEFT JOIN course_survey cs ON cs.survey_id = s.survey_id
            LEFT JOIN course c ON cs.course_id = c.course_id
            LEFT JOIN users evaluator ON sr.evaluator_id = evaluator.user_id
                       
            WHERE sr.evaluator_id = :evaluatorId
                       
            ORDER BY sr.updated_at DESC;
            """;

    public static final String GET_ALL_ROLE_PERMISSIONS= """
                SELECT
                    r.role_id AS roleId,
                    r.role_name AS roleName,
                    p.permission_id AS permissionId,
                    p.permission_name AS permissionName
                FROM role r
                LEFT JOIN role_permission rp ON r.role_id = rp.role_id
                LEFT JOIN permission p ON rp.permission_id = p.permission_id
                ORDER BY r.role_id, p.permission_id;
            """;

    public static final String GET_DETAIL_ROLE_PERMISSIONS= """
                SELECT
                        r.role_id AS roleId,
                        r.role_name AS roleName,
                        p.permission_id AS permissionId,
                        p.permission_name AS permissionName
                    FROM role r
                    LEFT JOIN role_permission rp ON r.role_id = rp.role_id
                    LEFT JOIN permission p ON rp.permission_id = p.permission_id
                    WHERE r.role_id = :roleId
                    ORDER BY p.permission_id;
            """;
}
