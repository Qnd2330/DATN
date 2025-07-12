# API Filter cho Danh sách Sinh viên

## Tổng quan

API Filter được thêm vào để hỗ trợ tìm kiếm và lọc dữ liệu cho 2 tab:
1. **Danh sách sinh viên chưa đánh giá** (Incomplete Surveys)
2. **Danh sách sinh viên đã làm báo cáo** (Completed Surveys)

## API Endpoints

### 1. Incomplete Surveys Filter

#### GET `/incomplete-surveys/list/{viewerId}/filter`

Lấy danh sách sinh viên chưa đánh giá với các filter tùy chọn.

**Parameters:**
- `viewerId` (path): ID của người xem (required)
- `userName` (query): Tên sinh viên (optional) - tìm kiếm theo pattern LIKE
- `facultyName` (query): Tên khoa (optional) - tìm kiếm theo pattern LIKE
- `className` (query): Tên lớp (optional) - tìm kiếm theo pattern LIKE
- `surveyName` (query): Tên khảo sát (optional) - tìm kiếm theo pattern LIKE
- `deadlineFrom` (query): Hạn nộp từ (optional) - format: YYYY-MM-DD
- `deadlineTo` (query): Hạn nộp đến (optional) - format: YYYY-MM-DD

**Ví dụ:**
```bash
# Tìm theo tên sinh viên
GET /incomplete-surveys/list/1/filter?userName=Nguyen

# Tìm theo khoa và lớp
GET /incomplete-surveys/list/1/filter?facultyName=Công nghệ thông tin&className=CNTT1

# Tìm theo khoảng thời gian
GET /incomplete-surveys/list/1/filter?deadlineFrom=2024-01-01&deadlineTo=2024-12-31
```

### 2. Completed Surveys Filter

#### GET `/completed-survey-students/{viewerId}/filter`

Lấy danh sách sinh viên đã làm báo cáo với các filter tùy chọn.

**Parameters:**
- `viewerId` (path): ID của người xem (required)
- `userName` (query): Tên sinh viên (optional) - tìm kiếm theo pattern LIKE
- `facultyName` (query): Tên khoa (optional) - tìm kiếm theo pattern LIKE
- `className` (query): Tên lớp (optional) - tìm kiếm theo pattern LIKE
- `surveyTitle` (query): Tên báo cáo (optional) - tìm kiếm theo pattern LIKE
- `submitTimeFrom` (query): Thời gian nộp từ (optional) - format: YYYY-MM-DDTHH:mm:ss
- `submitTimeTo` (query): Thời gian nộp đến (optional) - format: YYYY-MM-DDTHH:mm:ss

**Ví dụ:**
```bash
# Tìm theo tên sinh viên
GET /completed-survey-students/1/filter?userName=Nguyen

# Tìm theo khoa và lớp
GET /completed-survey-students/1/filter?facultyName=Công nghệ thông tin&className=CNTT1

# Tìm theo khoảng thời gian
GET /completed-survey-students/1/filter?submitTimeFrom=2024-01-01T00:00:00&submitTimeTo=2024-12-31T23:59:59
```

#### GET `/completed-survey-students/{viewerId}/filter/paginated`

Lấy danh sách sinh viên đã làm báo cáo với filter và phân trang.

**Parameters:**
- `viewerId` (path): ID của người xem (required)
- `page` (query): Số trang (default: 0)
- `size` (query): Kích thước trang (default: 10)
- `userName` (query): Tên sinh viên (optional)
- `facultyName` (query): Tên khoa (optional)
- `className` (query): Tên lớp (optional)
- `surveyTitle` (query): Tên báo cáo (optional)
- `submitTimeFrom` (query): Thời gian nộp từ (optional)
- `submitTimeTo` (query): Thời gian nộp đến (optional)

**Ví dụ:**
```bash
# Lấy trang đầu tiên với 10 bản ghi
GET /completed-survey-students/1/filter/paginated?page=0&size=10&userName=Nguyen

# Lấy trang thứ 2 với 5 bản ghi
GET /completed-survey-students/1/filter/paginated?page=1&size=5&facultyName=Công nghệ thông tin
```

## Response Format

### Success Response
```json
{
  "content": [
    {
      "userId": "SV001",
      "userName": "Nguyễn Văn A",
      "facultyName": "Công nghệ thông tin",
      "className": "CNTT1",
      "surveyName": "Đánh giá môn học",
      "deadline": "2024-12-31"
    }
  ],
  "currentPage": 0,
  "totalItems": 100,
  "totalPages": 10
}
```

### Error Response
```json
{
  "error": "Internal Server Error",
  "message": "Lỗi khi lấy danh sách sinh viên chưa hoàn thành đánh giá với filter"
}
```

## Tính năng Filter

### 1. Tìm kiếm theo Pattern (LIKE)
- `userName`: Tìm kiếm tên sinh viên chứa từ khóa
- `facultyName`: Tìm kiếm tên khoa chứa từ khóa
- `className`: Tìm kiếm tên lớp chứa từ khóa
- `surveyName`/`surveyTitle`: Tìm kiếm tên khảo sát/báo cáo chứa từ khóa

### 2. Tìm kiếm theo Khoảng thời gian
- `deadlineFrom`/`deadlineTo`: Lọc theo hạn nộp (Incomplete Surveys)
- `submitTimeFrom`/`submitTimeTo`: Lọc theo thời gian nộp (Completed Surveys)

### 3. Kết hợp nhiều Filter
Có thể kết hợp nhiều filter cùng lúc để có kết quả chính xác hơn.

## Bảo mật

- Tất cả endpoints đều yêu cầu JWT token hợp lệ
- Chỉ người dùng có quyền `READ_ACCESS` mới có thể truy cập
- Dữ liệu được lọc theo quyền của người dùng (faculty, role)

## Testing

Sử dụng file `test_filter_api.http` để test các API endpoints:

```bash
# Chạy test với REST Client extension trong VS Code
# Hoặc sử dụng Postman/Insomnia
```

## Lưu ý

1. **Case-insensitive**: Tất cả tìm kiếm theo text đều không phân biệt hoa thường
2. **Optional Parameters**: Tất cả filter parameters đều optional
3. **Date Format**: 
   - Incomplete Surveys: `YYYY-MM-DD`
   - Completed Surveys: `YYYY-MM-DDTHH:mm:ss`
4. **Pagination**: Chỉ có sẵn cho Completed Surveys với filter
5. **Performance**: Query được tối ưu với index và CTE (Common Table Expression) 