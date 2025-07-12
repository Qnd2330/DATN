package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.RegisterRequest;
import com.vn.DATN.DTO.request.StudentDTO;
import com.vn.DATN.DTO.request.UserDTO;
import com.vn.DATN.DTO.request.UserSearchFilterDTO;
import com.vn.DATN.Service.UsersService;
import com.vn.DATN.Service.repositories.RoleRepo;
import com.vn.DATN.Service.repositories.UserRepo;
import com.vn.DATN.entity.Role;
import com.vn.DATN.entity.Survey;
import com.vn.DATN.entity.Users;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    @Override
    public Page<Users> list(Pageable pageable) {
        return userRepo.findAllNotDeleted(pageable);
    }

    @Override
    public List<Users> getAll() {
        return userRepo.findUnassignedStudents();
    }

    @Override
    public List<Users> getAllManager() {
        return userRepo.findAllManager();
    }

    @Override
    @Transactional
    public List<Users> addMultipleStudents(List<StudentDTO> studentDTOList) {
        // Lấy role STUDENT 1 lần
        Role studentRole = roleRepo.findByRoleName("STUDENT")
                .orElseThrow(() -> new RuntimeException("Role STUDENT not found"));

        List<Users> usersToSave = new ArrayList<>();

        for (StudentDTO dto : studentDTOList) {
            // Bỏ qua nếu user đã tồn tại
            if (userRepo.existsById(dto.getStudentId())) {
                continue;
            }

            Users user = new Users();
            user.setUserId(dto.getStudentId());
            user.setUserName(dto.getStudentName());
            user.setPassword(passwordEncoder.encode(String.valueOf(dto.getStudentId()))); // Mật khẩu = mã sinh viên
            user.getRoles().add(studentRole); // Gán role STUDENT

            usersToSave.add(user);
        }

        return userRepo.saveAll(usersToSave);
    }

    @Override
    @Transactional
    public List<Users> importStudentsFromExcel(MultipartFile file, String roleName) {
        List<Users> students = new ArrayList<>();
        
        // Lấy role từ database
        Role role = roleRepo.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role " + roleName + " không tồn tại"));
        
        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Users student = new Users();
                student.setUserId((int) row.getCell(0).getNumericCellValue());
                student.setUserName(row.getCell(1).getStringCellValue());
                student.setPhoneNumber((int) row.getCell(2).getNumericCellValue());
                student.setEmail(row.getCell(3).getStringCellValue());
                // Nếu có cột ngày sinh (cột 4, index 4), parse ngày linh hoạt
                if (row.getLastCellNum() > 4 && row.getCell(4) != null) {
                    Cell cell = row.getCell(4);
                    if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        student.setBirthDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    } else if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().isEmpty()) {
                        String birthDateStr = cell.getStringCellValue();
                        LocalDate birthDate = null;
                        DateTimeFormatter[] formatters = {
                            DateTimeFormatter.ofPattern("d/M/yyyy"),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                            DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        };
                        for (DateTimeFormatter fmt : formatters) {
                            try {
                                birthDate = LocalDate.parse(birthDateStr, fmt);
                                break;
                            } catch (Exception ignored) {}
                        }
                        if (birthDate == null) {
                            throw new RuntimeException("Không nhận diện được định dạng ngày sinh: " + birthDateStr);
                        }
                        student.setBirthDate(birthDate);
                    }
                }
                // Nếu có cột giới tính (cột 5, index 5), nhận giá trị NAM hoặc NU
                if (row.getLastCellNum() > 5 && row.getCell(5) != null && !row.getCell(5).getStringCellValue().isEmpty()) {
                    String genderStr = row.getCell(5).getStringCellValue();
                    student.setGender(genderStr);
                }
                student.setPassword(passwordEncoder.encode(String.valueOf(student.getUserId())));
                
                // Gán role cho người dùng
                student.getRoles().add(role);

                students.add(student);
            }

            // Lưu vào DB ngay tại đây
            userRepo.saveAll(students);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đọc hoặc lưu file Excel: " + e.getMessage());
        }
        return students;
    }


    @Override
    public Users create(RegisterRequest request) {
        Users users = new Users();
        users.setUserId(request.getUserId());
        users.setPassword(passwordEncoder.encode(String.valueOf(request.getPassword())));
        users.setUserName(request.getUserId().toString());
        return userRepo.save(users);
    }

    @Override
    public Users edit(UserDTO request) {
        Users users = userRepo.findById(request.getUserId()).orElseThrow(() ->new RuntimeException("Không tìm thấy ngươi dùng"));
        users.setUserId(request.getUserId());
        users.setUserName(request.getUserName());
        users.setEmail(request.getEmail());
        users.setPhoneNumber(request.getPhoneNumber());
        // Xử lý birthDate nếu có
        if (request.getBirthDate() != null && !request.getBirthDate().isEmpty()) {
            users.setBirthDate(LocalDate.parse(request.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        // Xử lý gender nếu có
        if (request.getGender() != null && !request.getGender().isEmpty()) {
            users.setGender(request.getGender());
        }
        return userRepo.saveAndFlush(users);
    }

    @Override
    public void changePassword(Users users, String newPassword) {
        users.setPassword(passwordEncoder.encode(newPassword));
        userRepo.saveAndFlush(users);
    }

    @Override
    public void delete(Integer userId) {
        Users users = userRepo.findById(userId).orElseThrow(() ->new RuntimeException("Không tìm thấy ngươi dùng"));
        users.setDeleted(true);
        userRepo.saveAndFlush(users);
    }

    @Override
    public Users findById(Integer userId) {
        return userRepo.findById(userId).orElse(null);
    }

    @Override
    public Users findByUserName(String userName) {
        Users users = userRepo.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("không tìm thấy người dùng"));
        return users;
    }

    @Override
    public Users addRoleToUser(Integer userId, String roleName) {
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepo.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().clear();

        user.getRoles().add(role);

        return userRepo.saveAndFlush(user);
    }

    @Override
    public List<String> getAllRoleNames() {
        return roleRepo.findAll().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Users> searchUsers(UserSearchFilterDTO filter, Pageable pageable) {
        Specification<Users> spec = Specification.where(null);
        
        // Tìm kiếm theo userName
        if (filter.getUserName() != null && !filter.getUserName().trim().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.like(criteriaBuilder.lower(root.get("userName")), 
                    "%" + filter.getUserName().toLowerCase() + "%"));
        }
        
        // Tìm kiếm theo email
        if (filter.getEmail() != null && !filter.getEmail().trim().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), 
                    "%" + filter.getEmail().toLowerCase() + "%"));
        }
        
        // Tìm kiếm theo phoneNumber
        if (filter.getPhoneNumber() != null && !filter.getPhoneNumber().trim().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.equal(root.get("phoneNumber"), 
                    Integer.parseInt(filter.getPhoneNumber())));
        }
        
        // Tìm kiếm theo birthDate
        if (filter.getBirthDate() != null && !filter.getBirthDate().trim().isEmpty()) {
            try {
                LocalDate birthDate = LocalDate.parse(filter.getBirthDate(), 
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                spec = spec.and((root, query, criteriaBuilder) -> 
                    criteriaBuilder.equal(root.get("birthDate"), birthDate));
            } catch (Exception e) {
                // Nếu parse lỗi thì bỏ qua filter này
            }
        }
        
        // Tìm kiếm theo gender
        if (filter.getGender() != null && !filter.getGender().trim().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> 
                criteriaBuilder.equal(root.get("gender"), filter.getGender()));
        }
        
        // Tìm kiếm theo role
        if (filter.getRole() != null && !filter.getRole().trim().isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                query.distinct(true);
                return criteriaBuilder.equal(root.join("roles").get("roleName"), filter.getRole());
            });
        }
        
        // Chỉ lấy users chưa bị xóa
        spec = spec.and((root, query, criteriaBuilder) -> 
            criteriaBuilder.equal(root.get("deleted"), false));
        
        return userRepo.findAll(spec, pageable);
    }
    
    @Override
    public List<Users> getUsersByManagerOrTeacherRole() {
        return userRepo.findUsersByManagerOrTeacherRole();
    }
}
