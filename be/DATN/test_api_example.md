# Test API Thống Kê

## Cách Test API

### 1. Lấy JWT Token
Trước tiên, bạn cần login để lấy token:

```bash
curl -X POST http://localhost:2330/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "YOUR_USER_ID",
    "password": "YOUR_PASSWORD"
  }'
```

### 2. Test API Thống Kê

#### Test Overview
```bash
curl -X GET http://localhost:2330/statistics/overview \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Response mong đợi:**
```json
{
  "totalSurveys": 5,
  "totalStudents": 150,
  "completedSurveys": 120,
  "completionRate": 80.0
}
```

#### Test By Faculty
```bash
curl -X GET http://localhost:2330/statistics/by-faculty \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Response mong đợi:**
```json
[
  {
    "facultyName": "Công nghệ thông tin",
    "totalStudents": 50,
    "totalSurveys": 5,
    "completedSurveys": 45,
    "completionRate": 90.0
  }
]
```

#### Test By Class
```bash
curl -X GET http://localhost:2330/statistics/by-class \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Response mong đợi:**
```json
[
  {
    "className": "CNTT-K65",
    "totalStudents": 30,
    "totalSurveys": 5,
    "completedSurveys": 28,
    "completionRate": 93.33
  }
]
```

#### Test All Statistics
```bash
curl -X GET http://localhost:2330/statistics/all \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Lưu ý
- Thay `YOUR_JWT_TOKEN` bằng token thực từ login
- Chỉ ADMIN và TEACHER mới có quyền truy cập
- Nếu không có dữ liệu, API sẽ trả về giá trị 0 hoặc mảng rỗng 