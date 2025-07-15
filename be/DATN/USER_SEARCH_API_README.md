# API Tìm Kiếm User Động

## Tổng quan
API này cho phép tìm kiếm user theo nhiều tiêu chí khác nhau một cách linh hoạt, hỗ trợ phân trang và có thể kết hợp nhiều điều kiện tìm kiếm.

## Endpoint
```
POST /users/search
```

## Quyền truy cập
- Yêu cầu quyền: `READ_ACCESS`

## Tham số

### Query Parameters
- `page` (optional): Số trang, mặc định là 0
- `size` (optional): Số lượng item trên mỗi trang, mặc định là 10

### Request Body (UserSearchFilterDTO)
```json
{
  "userName": "string",      // Tìm kiếm theo tên user (tìm kiếm mờ)
  "email": "string",         // Tìm kiếm theo email (tìm kiếm mờ)
  "phoneNumber": "string",   // Tìm kiếm theo số điện thoại (chính xác)
  "birthDate": "string",     // Tìm kiếm theo ngày sinh (format: yyyy-MM-dd)
  "gender": "string",        // Tìm kiếm theo giới tính (NAM hoặc NU)
  "role": "string",          // Tìm kiếm theo role (STUDENT, TEACHER, ADMIN, MANAGER)
  "facultyName": "string",   // Tìm kiếm theo tên khoa (chưa implement)
  "className": "string"      // Tìm kiếm theo tên lớp (chưa implement)
}
```

## Response
```json
{
  "data": [
    {
      "userId": 123,
      "userName": "Nguyễn Văn A",
      "email": "nguyenvana@gmail.com",
      "phoneNumber": 123456789,
      "birthDate": "2000-01-01",
      "gender": "NAM",
      "roles": ["STUDENT"]
    }
  ],
  "page": 0,
  "totalElements": 50,
  "totalPages": 5
}
```

## Ví dụ sử dụng

### 1. Tìm kiếm theo tên user
```bash
POST /users/search?page=0&size=10
Content-Type: application/json

{
  "userName": "Nguyễn"
}
```

### 2. Tìm kiếm theo email
```bash
POST /users/search?page=0&size=10
Content-Type: application/json

{
  "email": "gmail"
}
```

### 3. Tìm kiếm theo số điện thoại
```bash
POST /users/search?page=0&size=10
Content-Type: application/json

{
  "phoneNumber": "0123"
}
```

### 4. Tìm kiếm theo ngày sinh
```bash
POST /users/search?page=0&size=10
Content-Type: application/json

{
  "birthDate": "2000-01-01"
}
```

### 5. Tìm kiếm theo giới tính
```bash
POST /users/search?page=0&size=10
Content-Type: application/json

{
  "gender": "NAM"
}
```

### 6. Tìm kiếm theo role
```bash
POST /users/search?page=0&size=10
Content-Type: application/json

{
  "role": "STUDENT"
}
```

### 7. Tìm kiếm kết hợp nhiều điều kiện
```bash
POST /users/search?page=0&size=10
Content-Type: application/json

{
  "userName": "Nguyễn",
  "gender": "NAM",
  "role": "STUDENT"
}
```

### 8. Tìm kiếm không có filter (lấy tất cả)
```bash
POST /users/search?page=0&size=10
Content-Type: application/json

{}
```

## Lưu ý

1. **Tìm kiếm mờ**: Các trường `userName` và `email` sử dụng tìm kiếm mờ (LIKE), không phân biệt hoa thường
2. **Tìm kiếm chính xác**: Các trường `phoneNumber`, `birthDate`, `gender`, `role` sử dụng tìm kiếm chính xác
3. **Kết hợp điều kiện**: Có thể kết hợp nhiều điều kiện tìm kiếm, tất cả điều kiện sẽ được kết hợp bằng AND
4. **Phân trang**: Luôn trả về kết quả có phân trang
5. **Chỉ lấy user chưa xóa**: API chỉ trả về các user có `deleted = false`
6. **Xử lý lỗi**: Nếu parse ngày sinh lỗi, filter đó sẽ bị bỏ qua

## Cấu trúc code

### DTO
- `UserSearchFilterDTO`: Chứa các trường filter
- `UserDTO`: DTO hiện có, không thay đổi

### Service
- `UsersService.searchUsers()`: Interface method
- `UsersServiceImpl.searchUsers()`: Implementation sử dụng JPA Specification

### Repository
- `UserRepo`: Kế thừa `JpaSpecificationExecutor<Users>` để hỗ trợ Specification

### Controller
- `UsersController.searchUsers()`: Endpoint xử lý request

## Mở rộng trong tương lai

1. Thêm tìm kiếm theo `facultyName` và `className`
2. Thêm sắp xếp theo các trường
3. Thêm tìm kiếm theo khoảng thời gian
4. Thêm tìm kiếm theo trạng thái active/inactive 