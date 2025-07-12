# API Import Users với Role Selection

## 📋 **Mô tả**
API này cho phép import danh sách người dùng từ file Excel và tự động gán role cho họ.

## 🔗 **Endpoints**

### 1. Lấy danh sách roles có sẵn
```
GET /users/roles
```

### 2. Import users với role
```
POST /users/import-students
```

## 📝 **Tham số**

### GET /users/roles
- **Headers**: Authorization Bearer token
- **Response**: Danh sách tên các role có sẵn

### POST /users/import-students
- **Headers**: Authorization Bearer token
- **Content-Type**: multipart/form-data
- **Body Parameters**:
  - `file` (MultipartFile, required): File Excel chứa danh sách người dùng
  - `roleName` (String, optional): Tên role muốn gán (default: "STUDENT")

## 🔐 **Quyền truy cập**
- Không yêu cầu quyền đặc biệt cho import
- Yêu cầu `READ_ACCESS` cho lấy danh sách roles

## 📊 **Response Format**

### GET /users/roles (200 OK)
```json
[
  "STUDENT",
  "TEACHER", 
  "MANAGER",
  "MONITOR",
  "ADMIN"
]
```

### POST /users/import-students (200 OK)
```json
"Thêm 5 người dùng với role TEACHER thành công!"
```

### Error Response (400 Bad Request)
```json
"Role TEACHER không tồn tại"
```

## 📋 **Format File Excel**

File Excel phải có cấu trúc như sau:

| Cột | Tên | Kiểu dữ liệu | Bắt buộc | Mô tả |
|-----|-----|--------------|----------|-------|
| A | User ID | Number | ✅ | ID người dùng |
| B | User Name | String | ✅ | Tên người dùng |
| C | Phone Number | Number | ❌ | Số điện thoại |
| D | Email | String | ❌ | Email |
| E | Birth Date | String (yyyy-MM-dd) | ❌ | Ngày sinh |
| F | Gender | String (NAM/NU) | ❌ | Giới tính |

### Ví dụ dữ liệu Excel:
```
| User ID | User Name      | Phone Number | Email                    | Birth Date  | Gender |
|---------|----------------|--------------|--------------------------|-------------|--------|
| 2110900001 | Nguyễn Văn A  | 123456789   | nguyenvana@email.com    | 2000-01-01  | NAM    |
| 2110900002 | Trần Thị B    | 987654321   | tranthib@email.com      | 2000-02-02  | NU     |
```

## 🚀 **Cách sử dụng**

### 1. Lấy danh sách roles
```bash
curl -X GET \
  "http://localhost:2330/users/roles" \
  -H "Authorization: Bearer <your_token>"
```

### 2. Import users với role mặc định (STUDENT)
```bash
curl -X POST \
  "http://localhost:2330/users/import-students" \
  -H "Authorization: Bearer <your_token>" \
  -F "file=@students.xlsx"
```

### 3. Import users với role cụ thể
```bash
curl -X POST \
  "http://localhost:2330/users/import-students?roleName=TEACHER" \
  -H "Authorization: Bearer <your_token>" \
  -F "file=@teachers.xlsx"
```

### 4. Test với Postman
1. **Lấy roles**: `GET http://localhost:2330/users/roles`
2. **Import users**: `POST http://localhost:2330/users/import-students`
   - Body: form-data
   - Key: `file` (File)
   - Key: `roleName` (Text, optional)

## 🔧 **Logic hoạt động**

### 1. **Validation**:
- Kiểm tra file Excel có tồn tại và đúng format
- Kiểm tra role có tồn tại trong database
- Kiểm tra User ID không trùng lặp

### 2. **Processing**:
- Đọc file Excel từ dòng 2 (bỏ qua header)
- Parse dữ liệu theo format quy định
- Mã hóa password = User ID
- Gán role được chọn cho tất cả users

### 3. **Saving**:
- Lưu tất cả users vào database trong một transaction
- Rollback nếu có lỗi xảy ra

## 📋 **Các role có sẵn**

- **STUDENT**: Sinh viên
- **TEACHER**: Giáo viên  
- **MANAGER**: Quản lý
- **MONITOR**: Lớp trưởng
- **ADMIN**: Quản trị viên

## ⚠️ **Lưu ý quan trọng**

1. **Password**: Mật khẩu mặc định = User ID (đã được mã hóa)
2. **Role**: Tất cả users trong file sẽ được gán cùng một role
3. **Duplicate**: Nếu User ID đã tồn tại, sẽ bỏ qua user đó
4. **Transaction**: Toàn bộ quá trình import được thực hiện trong một transaction
5. **File Format**: Chỉ hỗ trợ file Excel (.xlsx)

## 🧪 **Test Cases**

### Test Case 1: Import students
- File: students.xlsx
- Role: STUDENT (default)
- Expected: Thêm thành công với role STUDENT

### Test Case 2: Import teachers  
- File: teachers.xlsx
- Role: TEACHER
- Expected: Thêm thành công với role TEACHER

### Test Case 3: Invalid role
- File: users.xlsx
- Role: INVALID_ROLE
- Expected: Lỗi "Role INVALID_ROLE không tồn tại"

### Test Case 4: Invalid file format
- File: users.txt
- Expected: Lỗi "Lỗi khi đọc hoặc lưu file Excel" 