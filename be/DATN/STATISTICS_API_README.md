# API Thống Kê - Chỉ dành cho MANAGER

## Tổng quan

Hệ thống thống kê đã được nâng cấp với các tính năng mới:

1. **Phân quyền nghiêm ngặt**: Chỉ MANAGER mới có thể truy cập thống kê
2. **Lọc theo faculty**: MANAGER chỉ có thể xem thống kê của các faculty mà họ được liên kết
3. **Thống kê theo thời gian**: Có thể chọn khoảng thời gian cụ thể để xem thống kê
4. **3 loại thống kê**: Tổng quát, theo khoa, theo lớp

## Phân quyền

- **Chỉ MANAGER** mới có thể truy cập các API thống kê
- MANAGER chỉ có thể xem thống kê của các **faculty mà họ được liên kết** (thông qua bảng `faculty_user`)
- Các role khác (ADMIN, TEACHER, STUDENT) không thể truy cập

## API Endpoints

### 1. Thống Kê Tổng Quát

#### Lấy thống kê tổng thể
```
GET /statistics/overview
```

**Response:**
```json
{
  "totalSurveys": 10,
  "totalStudents": 150,
  "completedSurveys": 120,
  "completionRate": 80.0
}
```

#### Lấy thống kê tổng thể theo ngày tháng
```
GET /statistics/overview/by-date?startDate=2024-01-01&endDate=2024-12-31
```

**Parameters:**
- `startDate` (required): Ngày bắt đầu (format: yyyy-MM-dd)
- `endDate` (required): Ngày kết thúc (format: yyyy-MM-dd)

### 2. Thống Kê Theo Khoa

#### Lấy thống kê theo khoa
```
GET /statistics/by-faculty
```

**Response:**
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

#### Lấy thống kê theo khoa theo ngày tháng
```
GET /statistics/by-faculty/by-date?startDate=2024-01-01&endDate=2024-12-31
```

### 3. Thống Kê Theo Lớp

#### Lấy thống kê theo lớp
```
GET /statistics/by-class
```

**Response:**
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

#### Lấy thống kê theo lớp theo ngày tháng
```
GET /statistics/by-class/by-date?startDate=2024-01-01&endDate=2024-12-31
```

### 4. Lấy Tất Cả Thống Kê

#### Lấy tất cả thống kê trong một request
```
GET /statistics/all
```

**Response:**
```json
{
  "overview": {
    "totalSurveys": 10,
    "totalStudents": 150,
    "completedSurveys": 120,
    "completionRate": 80.0
  },
  "facultyStatistics": [
    {
      "facultyName": "Công nghệ thông tin",
      "totalStudents": 50,
      "totalSurveys": 5,
      "completedSurveys": 45,
      "completionRate": 90.0
    }
  ],
  "classStatistics": [
    {
      "className": "CNTT-K65",
      "totalStudents": 30,
      "totalSurveys": 5,
      "completedSurveys": 28,
      "completionRate": 93.33
    }
  ]
}
```

## Authentication

Tất cả các API yêu cầu JWT token của MANAGER trong header:
```
Authorization: Bearer <manager_jwt_token>
```

## Logic Lọc Theo Faculty

Hệ thống tự động lọc dữ liệu dựa trên:
1. **Role check**: Chỉ MANAGER mới có thể truy cập
2. **Faculty filtering**: Chỉ hiển thị thống kê của các faculty trong bảng `faculty_user` với `user_id = managerId`

### SQL Logic:
```sql
AND f.faculty_id IN (
  SELECT DISTINCT faculty_id 
  FROM faculty_user 
  WHERE user_id = :managerId
)
```

## Lưu Ý Quan Trọng

1. **Phân quyền nghiêm ngặt**: Chỉ MANAGER mới có thể truy cập
2. **Lọc tự động**: Dữ liệu được lọc tự động theo faculty liên kết
3. **Format ngày tháng**: Sử dụng format ISO (yyyy-MM-dd)
4. **Error handling**: Tất cả API đều có xử lý lỗi và trả về HTTP status code phù hợp
5. **Performance**: Các query đã được tối ưu hóa với proper indexing

## Ví Dụ Sử Dụng

### 1. Lấy thống kê tổng thể
```bash
curl -X GET "http://localhost:2330/statistics/overview" \
  -H "Authorization: Bearer manager_token_here"
```

### 2. Lấy thống kê theo khoa trong tháng 12/2024
```bash
curl -X GET "http://localhost:2330/statistics/by-faculty/by-date?startDate=2024-12-01&endDate=2024-12-31" \
  -H "Authorization: Bearer manager_token_here"
```

### 3. Lấy tất cả thống kê
```bash
curl -X GET "http://localhost:2330/statistics/all" \
  -H "Authorization: Bearer manager_token_here"
```

## Database Schema

### Bảng liên quan:
- `survey`: Phiếu đánh giá
- `survey_result`: Kết quả đánh giá
- `users`: Người dùng
- `faculty`: Khoa
- `class`: Lớp
- `user_class`: Quan hệ user-class
- `role_user`: Quan hệ user-role
- `role`: Vai trò 
- `faculty_user`: Quan hệ faculty-manager (quan trọng cho phân quyền)

### Query Optimization:
- Sử dụng LEFT JOIN để đảm bảo không mất dữ liệu
- Có proper GROUP BY và ORDER BY
- Sử dụng subquery để lọc theo faculty
- Có proper indexing cho các trường thường xuyên query

## Security

1. **Role-based access control**: Chỉ MANAGER mới có thể truy cập
2. **Data isolation**: MANAGER chỉ thấy dữ liệu của faculty họ quản lý
3. **JWT authentication**: Yêu cầu token hợp lệ
4. **Input validation**: Validate format ngày tháng
5. **Error handling**: Không expose thông tin nhạy cảm trong error message 