package com.vn.DATN.Controller;

import com.vn.DATN.Common.BasicBeanRemote;
import com.vn.DATN.DTO.request.*;
import com.vn.DATN.DTO.response.FacultyResponse;
import com.vn.DATN.DTO.response.PaginatedResponse;
import com.vn.DATN.DTO.response.UserResponse;
import com.vn.DATN.Service.EmailService;
import com.vn.DATN.Service.OtpService;
import com.vn.DATN.Service.UsersService;
import com.vn.DATN.entity.Faculty;
import com.vn.DATN.entity.Users;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    private final PasswordEncoder passwordEncoder;

    private final UsersService usersService;

    private final OtpService otpService;

    private final EmailService emailService;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
            try {
                Pageable pageable = PageRequest.of(page, size);
                Page<Users> usersPage = usersService.list(pageable);

                List<UserResponse> userResponse = usersPage
                        .stream()
                        .map(UserResponse::fromUser)
                        .collect(Collectors.toList());

                PaginatedResponse<UserResponse> response = new PaginatedResponse<>(
                        userResponse,
                        usersPage.getNumber(),
                        usersPage.getTotalElements(),
                        usersPage.getTotalPages()
                );
                return ResponseEntity.ok(response);
            } catch (RuntimeException ex) {
                log.error("Lỗi khi lấy danh sách người dùng", ex);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            }
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasAuthority('GET_USER_ACCESS') or hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getAll(){
        try{
            List<Users> users = usersService.getAll();
            List<UserResponse> userResponse = users
                    .stream()
                    .map(UserResponse::fromUser)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userResponse);
        }catch (RuntimeException ex){
            log.error("Lỗi khi lấy danh sách người dùng", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/get-all-manager")
    @PreAuthorize("hasAuthority('GET_USER_ACCESS') or hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getAllManager(){
        try{
            List<Users> users = usersService.getAllManager();
            List<UserResponse> userResponse = users
                    .stream()
                    .map(UserResponse::fromUser)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userResponse);
        }catch (RuntimeException ex){
            log.error("Lỗi khi lấy danh sách người dùng", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getAllRoles() {
        try {
            List<String> roles = usersService.getAllRoleNames();
            return ResponseEntity.ok(roles);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi lấy danh sách role", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/import-students")
    public ResponseEntity<?> importStudents(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "roleName", defaultValue = "STUDENT") String roleName) {
        try {
            List<Users> students = usersService.importStudentsFromExcel(file, roleName);
            return ResponseEntity.ok("Thêm " + students.size() + " người dùng với role " + roleName + " thành công!");
        } catch (RuntimeException ex) {
            log.error("Lỗi khi import người dùng", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ACCESS')")
    public ResponseEntity<?> create(@RequestBody RegisterRequest request) {
        try {
            Users created = usersService.create(request);

            UserResponse responseData = UserResponse.fromUser(created);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Thêm mới người dùng thành công");
            response.put("data", responseData);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi tạo người dùng", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UPDATE_ACCESS')")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        try {
            userDTO.setUserId(id);
            Users updated = usersService.edit(userDTO);
            UserResponse responseData = UserResponse.fromUser(updated);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Cập nhật người dùng thành công");
            response.put("data", responseData);

            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi cập nhật người dùng", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('DELETE_ACCESS')")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try{
            usersService.delete(id);
            return ResponseEntity.ok("Xóa người dùng thành công");
        }catch (RuntimeException ex) {
            log.error("Lỗi khi cập nhật người dùng", ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/send-otp")
    @PreAuthorize("hasAuthority('UPDATE_USER_ACCESS') or hasAuthority('UPDATE_ACCESS')")
    public ResponseEntity<?> sendOtp(@RequestParam Integer userId) {
       Users userOpt = usersService.findById(userId);
        if (userOpt == null) {
            return ResponseEntity.badRequest().body("Ngươời dùng không tồn tại ");
        }

        String otp = otpService.generateOtp(userOpt.getEmail());
        emailService.sendOtpEmail(userOpt.getEmail(), otp);

        return ResponseEntity.ok("Mã OTP đã được gửi đến email");
    }

    @PostMapping("/reset-password")
    @PreAuthorize("hasAuthority('UPDATE_USER_ACCESS') or hasAuthority('UPDATE_ACCESS')")
    public ResponseEntity<?> resetPassword(@RequestBody ChangePasswordRequest request) {
        Users user = usersService.findById(request.getUserId());
        if (user == null) {
            return ResponseEntity.badRequest().body("Người dùng không tồn tại");
        }

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Mật khẩu cũ không đúng");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body("Mật khẩu mới và xác nhận không khớp");
        }

        if (!otpService.validateOtp(user.getEmail(), request.getOtp())) {
            return ResponseEntity.badRequest().body("Mã OTP không đúng hoặc đã hết hạn");
        }

        usersService.changePassword(user, request.getNewPassword());
        otpService.clearOtp(user.getEmail());

        return ResponseEntity.ok("Đổi mật khẩu thành công");
    }

    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser() {
        return ResponseEntity.ok("This is your profile info");
    }

    @PostMapping("/{userId}/addRole/{roleName}")
    @PreAuthorize("hasAuthority('UPDATE_ACCESS')")
    public ResponseEntity<?> addRoleToUser(@PathVariable Integer userId, @PathVariable String roleName) {
        try {
            usersService.addRoleToUser(userId, roleName);
            return ResponseEntity.ok("Thêm chức vụ thành công");
        }catch (RuntimeException ex) {
            log.error("Lỗi không tìm thấy người dùng hoặc chức quyền", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/addListStudent")
    @PreAuthorize("hasAuthority('CREATE_ACCESS')")
    public ResponseEntity<?> addListStudent (@RequestBody List<StudentDTO> studentDTO) {
        try {
            List<Users> users = usersService.addMultipleStudents(studentDTO);
            List<UserResponse> userResponse = users
                    .stream()
                    .map(UserResponse::fromUser)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userResponse);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi thêm danh sách sinh viên", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> searchUsers(
            @RequestBody UserSearchFilterDTO filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Users> usersPage = usersService.searchUsers(filter, pageable);

            List<UserResponse> userResponse = usersPage
                    .stream()
                    .map(UserResponse::fromUser)
                    .collect(Collectors.toList());

            PaginatedResponse<UserResponse> response = new PaginatedResponse<>(
                    userResponse,
                    usersPage.getNumber(),
                    usersPage.getTotalElements(),
                    usersPage.getTotalPages()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi tìm kiếm người dùng", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    
    @GetMapping("/manager-teacher")
    @PreAuthorize("hasAuthority('READ_ACCESS')")
    public ResponseEntity<?> getUsersByManagerOrTeacherRole() {
        try {
            List<Users> users = usersService.getUsersByManagerOrTeacherRole();
            List<UserResponse> userResponse = users
                    .stream()
                    .map(UserResponse::fromUser)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userResponse);
        } catch (RuntimeException ex) {
            log.error("Lỗi khi lấy danh sách người dùng có role MANAGER hoặc TEACHER", ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}