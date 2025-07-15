# API Lấy Danh Sách Users Có Role MANAGER hoặc TEACHER

## Mô tả
API này cho phép lấy danh sách tất cả các user có role là MANAGER hoặc TEACHER trong hệ thống.

## Endpoint
```
GET /users/manager-teacher
```

## Quyền truy cập
- Yêu cầu quyền: `READ_ACCESS`

## Headers
```
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

## Response

### Thành công (200 OK)
```json
[
  {
    "userId": 1001,
    "userName": "Nguyễn Văn A",
    "email": "nguyenvana@example.com",
    "phoneNumber": 123456789,
    "gender": "NAM",
    "birthDate": "1990-01-01",
    "createAt": "2024-01-01T00:00:00",
    "updatedAt": "2024-01-01T00:00:00",
    "roles": [
      {
        "roleId": 1,
        "roleName": "MANAGER",
        "createAt": "2024-01-01T00:00:00",
        "updatedAt": "2024-01-01T00:00:00",
        "permissions": [
          {
            "permissionId": 1,
            "permissionName": "READ_ACCESS",
            "createAt": "2024-01-01T00:00:00",
            "updatedAt": "2024-01-01T00:00:00"
          }
        ]
      }
    ]
  },
  {
    "userId": 1002,
    "userName": "Trần Thị B",
    "email": "tranthib@example.com",
    "phoneNumber": 987654321,
    "gender": "NU",
    "birthDate": "1985-05-15",
    "createAt": "2024-01-01T00:00:00",
    "updatedAt": "2024-01-01T00:00:00",
    "roles": [
      {
        "roleId": 2,
        "roleName": "TEACHER",
        "createAt": "2024-01-01T00:00:00",
        "updatedAt": "2024-01-01T00:00:00",
        "permissions": [
          {
            "permissionId": 2,
            "permissionName": "CREATE_ACCESS",
            "createAt": "2024-01-01T00:00:00",
            "updatedAt": "2024-01-01T00:00:00"
          }
        ]
      }
    ]
  }
]
```

### Lỗi (400 Bad Request)
```json
{
  "message": "Lỗi khi lấy danh sách người dùng có role MANAGER hoặc TEACHER"
}
```

## Lưu ý
- API chỉ trả về các user chưa bị xóa (deleted = false)
- Kết quả được sắp xếp theo thứ tự mặc định của database
- Mỗi user có thể có nhiều role, nhưng API chỉ trả về những user có ít nhất một role là MANAGER hoặc TEACHER
- Thông tin password không được trả về trong response để đảm bảo bảo mật

## Sử dụng
API này thường được sử dụng để:
- Hiển thị danh sách quản lý và giảng viên trong dropdown
- Phân quyền và gán nhiệm vụ
- Thống kê số lượng quản lý và giảng viên trong hệ thống 