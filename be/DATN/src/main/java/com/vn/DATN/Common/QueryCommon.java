package com.vn.DATN.Common;

public class QueryCommon {
    public static final String GET_LIST_MANAGER = """
            SELECT u.*
            FROM users u
            JOIN role_user ru ON u.user_id = ru.user_id
            JOIN role r ON ru.role_id = r.role_id
            WHERE r.role_name = 'MANAGER'
              AND u.user_id NOT IN (
                  SELECT user_id FROM faculty_user
              )
              AND u.deleted = false;
            """;
    public static final String GET_LIST_FACULTY = """
            SELECT
                f.faculty_id as facultyId,
                f.faculty_name as facultyName,
                fu.user_id as userId,
                u.user_name as managerName,
                f.create_at as createAt,
                f.updated_at as updatedAt
            FROM Faculty f
            LEFT JOIN Faculty_User fu ON f.faculty_id = fu.faculty_id
            LEFT JOIN Users u ON fu.user_id = u.user_id
            ORDER BY f.faculty_id DESC           
                """;

    public static final String GET_DETAIL_FACULTY = """
            SELECT
                f.faculty_id AS facultyId,
                f.faculty_name AS facultyName,
                fu.user_id AS userId,
                u.user_name AS managerName,
                f.create_at AS createAt,
                f.updated_at AS updatedAt
            FROM Faculty f
            LEFT JOIN Faculty_User fu ON f.faculty_id = fu.faculty_id
            LEFT JOIN Users u ON fu.user_id = u.user_id
            WHERE f.faculty_id = :id
            ORDER BY f.faculty_id DESC;
            """;

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
            WHERE (r.role_name = 'STUDENT' OR r.role_name = 'MONITOR')
              AND u.user_id NOT IN (
                  SELECT user_id FROM user_class WHERE user_id IS NOT NULL
              )
              AND u.deleted = false;
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
              AND sr.id IS NULL
              AND (s.deadline IS NULL OR s.deadline >= CURRENT_DATE);
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
              AND (s.deadline IS NULL OR s.deadline >= CURRENT_DATE)      
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
                sr.id AS surveyResultId,
                s.survey_id AS surveyId,
                sr_student.id AS srStudentId,
                sr.user_id AS userId,
                s.title AS surveyTitle,
                u_target.user_name AS studentName,
                sr.evaluator_id AS evaluatorId,
                evaluator.user_name AS evaluatorName,
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
            WHERE sr.evaluator_id IS NOT NULL
              AND sr.evaluator_id != :teacherId
              AND (s.deadline IS NULL OR s.deadline >= CURRENT_DATE)       
              AND NOT EXISTS (
                  SELECT 1
                  FROM survey_result sr2
                  WHERE sr2.user_id = sr.user_id
                    AND sr2.survey_id = sr.survey_id
                    AND sr2.evaluator_id = :teacherId
              )
              AND (
                  -- Nếu teacher có role MANAGER: kiểm tra faculty_user
                  ('MANAGER' IN (
                      SELECT r2.role_name
                      FROM role_user ru2
                      JOIN role r2 ON ru2.role_id = r2.role_id
                      WHERE ru2.user_id = :teacherId
                  ) AND fu.user_id = :teacherId)     
                  OR    
                  -- Nếu teacher có role TEACHER: kiểm tra course.teach_id
                  ('TEACHER' IN (
                      SELECT r3.role_name
                      FROM role_user ru3
                      JOIN role r3 ON ru3.role_id = r3.role_id
                      WHERE ru3.user_id = :teacherId
                  ) AND co.teach_id = :teacherId)
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

    public static final String GET_ALL_ROLE_PERMISSIONS = """
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

    public static final String GET_DETAIL_ROLE_PERMISSIONS = """
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

    // Thống kê tổng thể hệ thống (chỉ cho MANAGER và faculty liên kết)
    public static final String GET_STATISTICS_OVERVIEW = """
            SELECT 
                COUNT(DISTINCT s.survey_id) AS totalSurveys,
                COUNT(DISTINCT u.user_id) AS totalStudents,
                COUNT(DISTINCT sr.survey_id || '-' || sr.user_id) AS completedSurveys,
                (
                    COUNT(DISTINCT sr.survey_id || '-' || sr.user_id) * 100.0 /
                    NULLIF(COUNT(DISTINCT s.survey_id) * COUNT(DISTINCT u.user_id), 0)
                ) AS completionRate
            FROM survey s
            CROSS JOIN (
                SELECT DISTINCT u.user_id
                FROM users u
                JOIN role_user ru ON u.user_id = ru.user_id
                JOIN role r ON ru.role_id = r.role_id
                JOIN user_class uc ON u.user_id = uc.user_id
                JOIN class c ON uc.class_id = c.class_id
                JOIN faculty f ON c.faculty_id = f.faculty_id
                WHERE r.role_name IN ('STUDENT', 'MONITOR')
                  AND f.faculty_id IN (
                    SELECT DISTINCT faculty_id 
                    FROM faculty_user 
                    WHERE user_id = :managerId
                  )
            ) u
            LEFT JOIN (
                SELECT survey_id, user_id
                FROM survey_result
                WHERE evaluator_id IS NULL OR evaluator_id = user_id
                GROUP BY survey_id, user_id
            ) sr ON sr.survey_id = s.survey_id AND sr.user_id = u.user_id
            WHERE (s.deadline IS NULL OR s.deadline >= CURRENT_DATE)
            """;

    // Thống kê theo khoa (chỉ cho MANAGER và faculty liên kết)
    public static final String GET_STATISTICS_BY_FACULTY = """
            SELECT 
                f.faculty_name AS facultyName,
                COUNT(DISTINCT u.user_id) AS totalStudents,
                COUNT(DISTINCT s.survey_id) AS totalSurveys,
                COUNT(DISTINCT sr.survey_id || '-' || sr.user_id) AS completedSurveys,
                (
                    COUNT(DISTINCT sr.survey_id || '-' || sr.user_id) * 100.0 /
                    NULLIF(COUNT(DISTINCT s.survey_id) * COUNT(DISTINCT u.user_id), 0)
                ) AS completionRate
            FROM faculty f
            LEFT JOIN class c ON f.faculty_id = c.faculty_id
            LEFT JOIN user_class uc ON c.class_id = uc.class_id
            LEFT JOIN users u ON uc.user_id = u.user_id
            LEFT JOIN role_user ru ON u.user_id = ru.user_id
            LEFT JOIN role r ON ru.role_id = r.role_id AND r.role_name IN ('STUDENT', 'MONITOR')
            LEFT JOIN survey s ON 1=1
            LEFT JOIN (
                SELECT survey_id, user_id
                FROM survey_result
                WHERE evaluator_id IS NULL OR evaluator_id = user_id
                GROUP BY survey_id, user_id
            ) sr ON sr.survey_id = s.survey_id AND sr.user_id = u.user_id
            WHERE u.user_id IS NOT NULL
              AND (s.deadline IS NULL OR s.deadline >= CURRENT_DATE)
              AND f.faculty_id IN (
                SELECT DISTINCT faculty_id 
                FROM faculty_user 
                WHERE user_id = :managerId
              )
            GROUP BY f.faculty_id, f.faculty_name
            """;

    // Thống kê theo lớp (chỉ cho MANAGER và faculty liên kết)
    public static final String GET_STATISTICS_BY_CLASS = """
            SELECT 
                c.class_name AS className,
                COUNT(DISTINCT u.user_id) AS totalStudents,
                COUNT(DISTINCT s.survey_id) AS totalSurveys,
                COUNT(DISTINCT sr.survey_id || '-' || sr.user_id) AS completedSurveys,
                (
                    COUNT(DISTINCT sr.survey_id || '-' || sr.user_id) * 100.0 /
                    NULLIF(COUNT(DISTINCT s.survey_id) * COUNT(DISTINCT u.user_id), 0)
                ) AS completionRate
            FROM class c
            LEFT JOIN user_class uc ON c.class_id = uc.class_id
            LEFT JOIN users u ON uc.user_id = u.user_id
            LEFT JOIN role_user ru ON u.user_id = ru.user_id
            LEFT JOIN role r ON ru.role_id = r.role_id AND r.role_name IN ('STUDENT', 'MONITOR')
            LEFT JOIN faculty f ON c.faculty_id = f.faculty_id
            LEFT JOIN survey s ON 1=1
            LEFT JOIN (
                SELECT survey_id, user_id
                FROM survey_result
                WHERE evaluator_id IS NULL OR evaluator_id = user_id
                GROUP BY survey_id, user_id
            ) sr ON sr.survey_id = s.survey_id AND sr.user_id = u.user_id
            WHERE u.user_id IS NOT NULL
              AND (s.deadline IS NULL OR s.deadline >= CURRENT_DATE)
              AND f.faculty_id IN (
                SELECT DISTINCT faculty_id 
                FROM faculty_user 
                WHERE user_id = :managerId
              )
            GROUP BY c.class_id, c.class_name
            """;

    public static final String GET_INCOMPLETE_SURVEYS_BY_VIEWER_ID = """
            WITH viewer_role AS (
                               SELECT r.role_name
                               FROM role_user ru
                               JOIN role r ON ru.role_id = r.role_id
                               WHERE ru.user_id = :viewerId
                               LIMIT 1
                           ),
                           viewer_faculty AS (
                               SELECT DISTINCT faculty_id
                               FROM faculty_user
                               WHERE user_id = :viewerId
                           ),
                           viewer_course AS (
                               SELECT DISTINCT cs.course_id
                               FROM course_survey cs
                               JOIN survey s ON s.survey_id = cs.survey_id
                               JOIN survey_result sr ON sr.survey_id = s.survey_id
                               WHERE sr.evaluator_id = :viewerId
                           ),
                           students AS (
                               SELECT DISTINCT
                                   u.user_id,
                                   u.user_name,
                                   c.class_id,
                                   c.class_name,
                                   f.faculty_id,
                                   f.faculty_name
                               FROM users u
                               JOIN role_user ru ON u.user_id = ru.user_id
                               JOIN role r ON ru.role_id = r.role_id
                               JOIN user_class uc ON u.user_id = uc.user_id
                               JOIN class c ON uc.class_id = c.class_id
                               JOIN faculty f ON c.faculty_id = f.faculty_id
                               WHERE r.role_name IN ('STUDENT', 'MONITOR')
                           )
                           SELECT
                               s.user_id as userId,
                               s.user_name as userName,
                               sv.title as surveyName,
                               s.faculty_name as facultyName,
                               s.class_name as className,
                               sv.deadline as deadline
                           FROM survey sv
                           CROSS JOIN students s
                           LEFT JOIN (
                               SELECT survey_id, user_id
                               FROM survey_result
                               WHERE evaluator_id IS NULL OR evaluator_id = user_id
                               GROUP BY survey_id, user_id
                           ) sr ON sr.survey_id = sv.survey_id AND sr.user_id = s.user_id
                           WHERE sr.survey_id IS NULL
                             AND (sv.deadline IS NULL OR sv.deadline >= CURRENT_DATE)
                             AND (
                               EXISTS (SELECT 1 FROM viewer_role WHERE role_name = 'MONITOR')
                               OR (
                                 EXISTS (SELECT 1 FROM viewer_role WHERE role_name = 'TEACHER')
                                 AND (
                                     EXISTS (
                                         SELECT 1
                                         FROM class_course cc
                                         WHERE cc.class_id = s.class_id
                                         AND cc.course_id IN (SELECT course_id FROM viewer_course)
                                     )
                                     OR s.faculty_id IN (SELECT faculty_id FROM viewer_faculty)
                                 )
                               )
                               OR (
                                 EXISTS (SELECT 1 FROM viewer_role WHERE role_name = 'MANAGER')
                                 AND s.faculty_id IN (SELECT faculty_id FROM viewer_faculty)
                               )
                             )
                           ORDER BY s.faculty_name, s.class_name, s.user_name, sv.deadline
            """;


    // Thống kê tổng thể theo ngày tháng (chỉ cho MANAGER và faculty liên kết)
    public static final String GET_STATISTICS_OVERVIEW_BY_DATE = """
            SELECT 
                COUNT(DISTINCT s.survey_id) AS totalSurveys,
                COUNT(DISTINCT u.user_id) AS totalStudents,
                COUNT(DISTINCT sr.survey_id || '-' || sr.user_id) AS completedSurveys,
                (
                    COUNT(DISTINCT sr.survey_id || '-' || sr.user_id) * 100.0 /
                    NULLIF(COUNT(DISTINCT s.survey_id) * COUNT(DISTINCT u.user_id), 0)
                ) AS completionRate
            FROM survey s
            CROSS JOIN (
                SELECT DISTINCT u.user_id
                FROM users u
                JOIN role_user ru ON u.user_id = ru.user_id
                JOIN role r ON ru.role_id = r.role_id
                JOIN user_class uc ON u.user_id = uc.user_id
                JOIN class c ON uc.class_id = c.class_id
                JOIN faculty f ON c.faculty_id = f.faculty_id
                WHERE r.role_name IN ('STUDENT', 'MONITOR')
                  AND f.faculty_id IN (
                    SELECT DISTINCT faculty_id 
                    FROM faculty_user 
                    WHERE user_id = :managerId
                  )
            ) u
            LEFT JOIN (
                SELECT survey_id, user_id
                FROM survey_result
                WHERE evaluator_id IS NULL OR evaluator_id = user_id
                GROUP BY survey_id, user_id
            ) sr ON sr.survey_id = s.survey_id AND sr.user_id = u.user_id
            WHERE (s.deadline IS NULL OR s.deadline >= CURRENT_DATE)
              AND s.create_at >= CAST(:startDate AS DATE)
              AND s.create_at <= CAST(:endDate AS DATE)
            """;

    // Thống kê theo khoa theo ngày tháng (chỉ cho MANAGER và faculty liên kết)
    public static final String GET_STATISTICS_BY_FACULTY_BY_DATE = """
            SELECT 
                f.faculty_name AS facultyName,
                COUNT(DISTINCT u.user_id) AS totalStudents,
                COUNT(DISTINCT s.survey_id) AS totalSurveys,
                COUNT(DISTINCT sr.survey_id || '-' || sr.user_id) AS completedSurveys,
                (
                    COUNT(DISTINCT sr.survey_id || '-' || sr.user_id) * 100.0 /
                    NULLIF(COUNT(DISTINCT s.survey_id) * COUNT(DISTINCT u.user_id), 0)
                ) AS completionRate
            FROM faculty f
            LEFT JOIN class c ON f.faculty_id = c.faculty_id
            LEFT JOIN user_class uc ON c.class_id = uc.class_id
            LEFT JOIN users u ON uc.user_id = u.user_id
            LEFT JOIN role_user ru ON u.user_id = ru.user_id
            LEFT JOIN role r ON ru.role_id = r.role_id AND r.role_name IN ('STUDENT', 'MONITOR')
            LEFT JOIN survey s ON 1=1
            LEFT JOIN (
                SELECT survey_id, user_id
                FROM survey_result
                WHERE evaluator_id IS NULL OR evaluator_id = user_id
                GROUP BY survey_id, user_id
            ) sr ON sr.survey_id = s.survey_id AND sr.user_id = u.user_id
            WHERE u.user_id IS NOT NULL
              AND (s.deadline IS NULL OR s.deadline >= CURRENT_DATE)
              AND s.create_at >= CAST(:startDate AS DATE)
              AND s.create_at <= CAST(:endDate AS DATE)
              AND f.faculty_id IN (
                SELECT DISTINCT faculty_id 
                FROM faculty_user 
                WHERE user_id = :managerId
              )
            GROUP BY f.faculty_id, f.faculty_name
            """;

    // Thống kê theo lớp theo ngày tháng (chỉ cho MANAGER và faculty liên kết)
    public static final String GET_STATISTICS_BY_CLASS_BY_DATE = """
            SELECT 
                c.class_name AS className,
                COUNT(DISTINCT u.user_id) AS totalStudents,
                COUNT(DISTINCT s.survey_id) AS totalSurveys,
                COUNT(DISTINCT sr.survey_id || '-' || sr.user_id) AS completedSurveys,
                (
                    COUNT(DISTINCT sr.survey_id || '-' || sr.user_id) * 100.0 /
                    NULLIF(COUNT(DISTINCT s.survey_id) * COUNT(DISTINCT u.user_id), 0)
                ) AS completionRate
            FROM class c
            LEFT JOIN user_class uc ON c.class_id = uc.class_id
            LEFT JOIN users u ON uc.user_id = u.user_id
            LEFT JOIN role_user ru ON u.user_id = ru.user_id
            LEFT JOIN role r ON ru.role_id = r.role_id AND r.role_name IN ('STUDENT', 'MONITOR')
            LEFT JOIN faculty f ON c.faculty_id = f.faculty_id
            LEFT JOIN survey s ON 1=1
            LEFT JOIN (
                SELECT survey_id, user_id
                FROM survey_result
                WHERE evaluator_id IS NULL OR evaluator_id = user_id
                GROUP BY survey_id, user_id
            ) sr ON sr.survey_id = s.survey_id AND sr.user_id = u.user_id
            WHERE u.user_id IS NOT NULL
              AND (s.deadline IS NULL OR s.deadline >= CURRENT_DATE)
              AND s.create_at >= CAST(:startDate AS DATE)
              AND s.create_at <= CAST(:endDate AS DATE)
              AND f.faculty_id IN (
                SELECT DISTINCT faculty_id 
                FROM faculty_user 
                WHERE user_id = :managerId
              )
            GROUP BY c.class_id, c.class_name
            """;

    // Lấy danh sách users có role MANAGER hoặc TEACHER
    public static final String GET_USERS_BY_MANAGER_OR_TEACHER_ROLE = """
            SELECT DISTINCT u.*
            FROM users u
            JOIN role_user ru ON u.user_id = ru.user_id
            JOIN role r ON ru.role_id = r.role_id
            WHERE r.role_name IN ('MANAGER', 'TEACHER')
              AND u.deleted = false
            ORDER BY u.user_id
            """;

    // Danh sách sinh viên đã làm báo cáo
    public static final String GET_COMPLETED_SURVEY_STUDENTS = """
            SELECT 
                u.user_id AS userId,
                u.user_name AS userName,
                f.faculty_name AS facultyName,
                c.class_name AS className,
                s.title AS surveyTitle,
                sr.create_at AS submitTime
            FROM users u
            JOIN role_user ru ON u.user_id = ru.user_id
            JOIN role r ON ru.role_id = r.role_id
            JOIN user_class uc ON u.user_id = uc.user_id
            JOIN class c ON uc.class_id = c.class_id
            JOIN faculty f ON c.faculty_id = f.faculty_id
            JOIN survey_result sr ON u.user_id = sr.user_id
            JOIN survey s ON sr.survey_id = s.survey_id
            WHERE r.role_name IN ('STUDENT', 'MONITOR')
              AND f.faculty_id IN (
                  SELECT faculty_id
                  FROM faculty_user
                  WHERE user_id = :viewerId
              )
              AND (sr.evaluator_id IS NULL OR sr.evaluator_id = u.user_id)
            ORDER BY sr.create_at DESC
            """;

    // Danh sách sinh viên chưa đánh giá với filter
    public static final String GET_INCOMPLETE_SURVEYS_BY_VIEWER_ID_WITH_FILTER = """
            WITH viewer_role AS (
                               SELECT r.role_name
                               FROM role_user ru
                               JOIN role r ON ru.role_id = r.role_id
                               WHERE ru.user_id = :viewerId
                               LIMIT 1
                           ),
                           viewer_faculty AS (
                               SELECT DISTINCT faculty_id
                               FROM faculty_user
                               WHERE user_id = :viewerId
                           ),
                           viewer_course AS (
                               SELECT DISTINCT cs.course_id
                               FROM course_survey cs
                               JOIN survey s ON s.survey_id = cs.survey_id
                               JOIN survey_result sr ON sr.survey_id = s.survey_id
                               WHERE sr.evaluator_id = :viewerId
                           ),
                           students AS (
                               SELECT DISTINCT
                                   u.user_id,
                                   u.user_name,
                                   c.class_id,
                                   c.class_name,
                                   f.faculty_id,
                                   f.faculty_name
                               FROM users u
                               JOIN role_user ru ON u.user_id = ru.user_id
                               JOIN role r ON ru.role_id = r.role_id
                               JOIN user_class uc ON u.user_id = uc.user_id
                               JOIN class c ON uc.class_id = c.class_id
                               JOIN faculty f ON c.faculty_id = f.faculty_id
                               WHERE r.role_name IN ('STUDENT', 'MONITOR')
                                 AND (:userId IS NULL OR u.user_id = CAST(:userId AS INTEGER))
                                 AND (:userName IS NULL OR LOWER(u.user_name) LIKE LOWER(CONCAT('%', :userName, '%')))
                                 AND (:facultyName IS NULL OR LOWER(f.faculty_name) LIKE LOWER(CONCAT('%', :facultyName, '%')))
                                 AND (:className IS NULL OR LOWER(c.class_name) LIKE LOWER(CONCAT('%', :className, '%')))
                           )
                           SELECT
                               s.user_id as userId,
                               s.user_name as userName,
                               sv.title as surveyName,
                               s.faculty_name as facultyName,
                               s.class_name as className,
                               sv.deadline as deadline
                           FROM survey sv
                           CROSS JOIN students s
                           LEFT JOIN (
                               SELECT survey_id, user_id
                               FROM survey_result
                               WHERE evaluator_id IS NULL OR evaluator_id = user_id
                               GROUP BY survey_id, user_id
                           ) sr ON sr.survey_id = sv.survey_id AND sr.user_id = s.user_id
                           WHERE sr.survey_id IS NULL
                             AND (sv.deadline IS NULL OR sv.deadline >= CURRENT_DATE)
                             AND (:surveyName IS NULL OR LOWER(sv.title) LIKE LOWER(CONCAT('%', :surveyName, '%')))
                             AND (:deadlineFrom IS NULL OR sv.deadline >= CAST(:deadlineFrom AS DATE))
                             AND (:deadlineTo IS NULL OR sv.deadline <= CAST(:deadlineTo AS DATE))
                             AND (
                               EXISTS (SELECT 1 FROM viewer_role WHERE role_name = 'MONITOR')
                               OR (
                                 EXISTS (SELECT 1 FROM viewer_role WHERE role_name = 'TEACHER')
                                 AND (
                                     EXISTS (
                                         SELECT 1
                                         FROM class_course cc
                                         WHERE cc.class_id = s.class_id
                                         AND cc.course_id IN (SELECT course_id FROM viewer_course)
                                     )
                                     OR s.faculty_id IN (SELECT faculty_id FROM viewer_faculty)
                                 )
                               )
                               OR (
                                 EXISTS (SELECT 1 FROM viewer_role WHERE role_name = 'MANAGER')
                                 AND s.faculty_id IN (SELECT faculty_id FROM viewer_faculty)
                               )
                             )
                           ORDER BY s.faculty_name, s.class_name, s.user_name, sv.deadline
            """;

    // Danh sách sinh viên đã làm báo cáo với filter
    public static final String GET_COMPLETED_SURVEY_STUDENTS_WITH_FILTER = """
            SELECT 
                u.user_id AS userId,
                u.user_name AS userName,
                f.faculty_name AS facultyName,
                c.class_name AS className,
                s.title AS surveyTitle,
                sr.create_at AS submitTime
            FROM users u
            JOIN role_user ru ON u.user_id = ru.user_id
            JOIN role r ON ru.role_id = r.role_id
            JOIN user_class uc ON u.user_id = uc.user_id
            JOIN class c ON uc.class_id = c.class_id
            JOIN faculty f ON c.faculty_id = f.faculty_id
            JOIN survey_result sr ON u.user_id = sr.user_id
            JOIN survey s ON sr.survey_id = s.survey_id
            WHERE r.role_name IN ('STUDENT', 'MONITOR')
              AND f.faculty_id IN (
                  SELECT faculty_id
                  FROM faculty_user
                  WHERE user_id = :viewerId
              )
              AND (sr.evaluator_id IS NULL OR sr.evaluator_id = u.user_id)
              AND (:userId IS NULL OR u.user_id = CAST(:userId AS INTEGER))
              AND (:userName IS NULL OR LOWER(u.user_name) LIKE LOWER(CONCAT('%', :userName, '%')))
              AND (:facultyName IS NULL OR LOWER(f.faculty_name) LIKE LOWER(CONCAT('%', :facultyName, '%')))
              AND (:className IS NULL OR LOWER(c.class_name) LIKE LOWER(CONCAT('%', :className, '%')))
              AND (:surveyTitle IS NULL OR LOWER(s.title) LIKE LOWER(CONCAT('%', :surveyTitle, '%')))
              AND (:submitTimeFrom IS NULL OR sr.create_at >= CAST(:submitTimeFrom AS TIMESTAMP))
              AND (:submitTimeTo IS NULL OR sr.create_at <= CAST(:submitTimeTo AS TIMESTAMP))
            ORDER BY sr.create_at DESC
            """;

}
