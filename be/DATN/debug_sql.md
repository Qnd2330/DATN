# Debug SQL Queries

## Kiểm tra SQL Query

### 1. Query Overview
```sql
SELECT 
    COUNT(DISTINCT s.survey_id) AS total_surveys,
    COUNT(DISTINCT u.user_id) AS total_students,
    COUNT(DISTINCT sr.survey_id || '-' || sr.user_id) AS completed_surveys,
    (
        COUNT(DISTINCT sr.survey_id || '-' || sr.user_id) * 100.0 /
        NULLIF(COUNT(DISTINCT s.survey_id) * COUNT(DISTINCT u.user_id), 0)
    ) AS completion_rate
FROM survey s
CROSS JOIN (
    SELECT DISTINCT u.user_id
    FROM users u
    JOIN role_user ru ON u.user_id = ru.user_id
    JOIN role r ON ru.role_id = r.role_id
    WHERE r.role_name IN ('STUDENT', 'MONITOR')
) u
LEFT JOIN (
    SELECT survey_id, user_id
    FROM survey_result
    WHERE evaluator_id IS NULL OR evaluator_id = user_id
    GROUP BY survey_id, user_id
) sr ON sr.survey_id = s.survey_id AND sr.user_id = u.user_id;
```

### 2. Kiểm tra dữ liệu trong database

#### Kiểm tra bảng survey:
```sql
SELECT COUNT(*) FROM survey;
SELECT * FROM survey LIMIT 5;
```

#### Kiểm tra bảng users với role STUDENT/MONITOR:
```sql
SELECT COUNT(*) FROM users u
JOIN role_user ru ON u.user_id = ru.user_id
JOIN role r ON ru.role_id = r.role_id
WHERE r.role_name IN ('STUDENT', 'MONITOR');
```

#### Kiểm tra bảng survey_result:
```sql
SELECT COUNT(*) FROM survey_result;
SELECT COUNT(*) FROM survey_result WHERE evaluator_id IS NULL;
SELECT COUNT(*) FROM survey_result WHERE evaluator_id = user_id;
```

### 3. Debug từng phần query

#### Phần 1: Đếm survey
```sql
SELECT COUNT(DISTINCT s.survey_id) AS total_surveys
FROM survey s;
```

#### Phần 2: Đếm students
```sql
SELECT COUNT(DISTINCT u.user_id) AS total_students
FROM users u
JOIN role_user ru ON u.user_id = ru.user_id
JOIN role r ON ru.role_id = r.role_id
WHERE r.role_name IN ('STUDENT', 'MONITOR');
```

#### Phần 3: Đếm completed surveys
```sql
SELECT COUNT(DISTINCT sr.survey_id || '-' || sr.user_id) AS completed_surveys
FROM survey_result sr
WHERE sr.evaluator_id IS NULL OR sr.evaluator_id = sr.user_id;
```

### 4. Kiểm tra kiểu dữ liệu trả về

Thêm log để debug:
```java
log.info("Raw result: {}", Arrays.toString(result));
log.info("Result[0] type: {}", result[0] != null ? result[0].getClass().getName() : "null");
log.info("Result[1] type: {}", result[1] != null ? result[1].getClass().getName() : "null");
log.info("Result[2] type: {}", result[2] != null ? result[2].getClass().getName() : "null");
log.info("Result[3] type: {}", result[3] != null ? result[3].getClass().getName() : "null");
```

### 5. Các trường hợp có thể xảy ra

#### Trường hợp 1: Không có dữ liệu
- `total_surveys = 0`
- `total_students = 0`
- `completed_surveys = 0`
- `completion_rate = null` (do chia cho 0)

#### Trường hợp 2: Có dữ liệu nhưng không có completed surveys
- `total_surveys > 0`
- `total_students > 0`
- `completed_surveys = 0`
- `completion_rate = 0.0`

#### Trường hợp 3: Có dữ liệu đầy đủ
- Tất cả các giá trị > 0
- `completion_rate` tính được 