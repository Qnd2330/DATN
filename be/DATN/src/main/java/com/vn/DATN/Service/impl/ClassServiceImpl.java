package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.AddUsersToClassDTO;
import com.vn.DATN.DTO.request.ClassDTO;
import com.vn.DATN.DTO.request.CourseDTO;
import com.vn.DATN.DTO.request.StudentDTO;
import com.vn.DATN.DTO.response.ClassDetailResponse;
import com.vn.DATN.DTO.response.UsersWithClassResponse;
import com.vn.DATN.Service.ClassService;
import com.vn.DATN.Service.FacultyService;
import com.vn.DATN.Service.repositories.ClassRepo;
import com.vn.DATN.Service.repositories.UserAndClassRepo;
import com.vn.DATN.Service.repositories.UserRepo;
import com.vn.DATN.entity.*;
import com.vn.DATN.entity.Class;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ClassRepo classRepo;
    private final UserAndClassRepo userAndClassRepo;

    private final UserRepo userRepo;
    private final FacultyService facultyService;

    @Override
    public Page<Class> list(Pageable pageable) {
        return classRepo.findAll(pageable);
    }

    @Override
    public ClassDetailResponse getClassDetail(Integer classId) {
        List<Object[]> rows = classRepo.findClassDetailByClassId(classId);

        Set<Integer> addedUserIds = new HashSet<>();
        Set<Integer> addedCourseIds = new HashSet<>();
        Map<Integer, String> teacherCache = new HashMap<>();

        List<StudentDTO> students = new ArrayList<>();
        List<CourseDTO> courses = new ArrayList<>();

        Object[] firstRow = rows.get(0);
        ClassDetailResponse response = new ClassDetailResponse();
        response.setClassId((Integer) firstRow[0]);
        response.setClassName((String) firstRow[1]);
        response.setTotalStudent((Integer) firstRow[2]);
        response.setFacultyName((String) firstRow[3]);

        for (Object[] row : rows) {
            Integer userId = (Integer) row[4];
            // --- Student Info ---
            if (userId != null && addedUserIds.add(userId)) {
                StudentDTO student = new StudentDTO();
                student.setStudentId(userId);
                student.setStudentName((String) row[5]);
                student.setPhone((int) row[6]);
                student.setEmail((String) row[7]);
                students.add(student);
            }

            // --- Course Info ---
            Integer courseId = (Integer) row[8];
            if (courseId != null && addedCourseIds.add(courseId)) {
                CourseDTO course = new CourseDTO();
                course.setCourseId(courseId);
                course.setCourseName((String) row[9]);

                Integer teacherId = (Integer) row[10];
                if (teacherId != null) {
                    String teacherName = teacherCache.computeIfAbsent(teacherId, id ->
                            userRepo.findById(id).map(Users::getUsername).orElse("Unknown")
                    );
                    course.setTeacherName(teacherName);
                } else {
                    course.setTeacherName("Unknown");
                }

                courses.add(course);
            }
        }

        response.setStudents(students);
        response.setCourses(courses);
        return response;
    }

    @Override
    public List<Users> getAll() {
        return userRepo.findAll();
    }

    @Override
    @Transactional
    public void addUsersToClass(AddUsersToClassDTO dto) {
        Class clazz = classRepo.findById(dto.getClassId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy lớp"));

        // Lấy danh sách user_class hiện tại
        List<UserClass> currentUserClasses = userAndClassRepo.findByClasses(clazz);

        // Tập userId hiện tại trong lớp
        Set<Integer> currentUserIds = currentUserClasses.stream()
                .map(uc -> uc.getUsers().getUserId())
                .collect(Collectors.toSet());

        // Tập userId mới gửi từ client
        Set<Integer> newUserIds = new HashSet<>(dto.getUserIds());

        // 1. Tìm những user cần THÊM
        Set<Integer> toAdd = new HashSet<>(newUserIds);
        toAdd.removeAll(currentUserIds);

        // 2. Tìm những user cần XÓA
        Set<Integer> toRemove = new HashSet<>(currentUserIds);
        toRemove.removeAll(newUserIds);

        // THÊM
        List<Users> usersToAdd = userRepo.findAllById(toAdd);
        List<UserClass> newUserClasses = new ArrayList<>();
        for (Users user : usersToAdd) {
            UserClass uc = new UserClass();
            uc.setUsers(user);
            uc.setClasses(clazz);
            newUserClasses.add(uc);
        }
        userAndClassRepo.saveAll(newUserClasses);

        // XÓA
        List<UserClass> toDelete = currentUserClasses.stream()
                .filter(uc -> toRemove.contains(uc.getUsers().getUserId()))
                .collect(Collectors.toList());
        userAndClassRepo.deleteAll(toDelete);
    }

    @Override
    public UsersWithClassResponse getall(String className, Pageable pageable) {
        Page<UserClass> page = userAndClassRepo.findByClasses_ClassName(className, pageable);

        if (page.isEmpty()) {
            throw new RuntimeException("Không tìm thấy lớp với tên: " + className);
        }

        Class clazz = page.getContent().get(0).getClasses();
        List<Users> users = page.getContent().stream()
                .map(UserClass::getUsers)
                .collect(Collectors.toList());
        return new UsersWithClassResponse(clazz, users, page.getTotalElements(), page.getTotalPages());
    }

    @Override
    @Transactional
    public Class create(ClassDTO classDTO) {
        Faculty faculty = facultyService.findByFacultyName(classDTO.getFacultyName());
        if(faculty == null) {
            throw new RuntimeException("Không tìm thấy Khoa");
        }
        Class lop = Class.builder()
                .className(classDTO.getClassName())
                .totalStudent(classDTO.getTotalStudent()) // Chưa xử lý hàm này
                .faculty(faculty)
                .build();
        return classRepo.save(lop);
    }

    @Override
    @Transactional
    public Class edit(ClassDTO classDTO) {
        Class lop = findById(classDTO.getClassID());
        if(lop == null) {
            throw new RuntimeException("Không tìm thấy lớp");
        }
        Faculty faculty = facultyService.findByFacultyName(classDTO.getFacultyName());
        if(faculty == null) {
            throw new RuntimeException("Không tìm thấy faculty");
        }
        lop.setClassName(classDTO.getClassName());
        lop.setFaculty(faculty);
        lop.setTotalStudent(classDTO.getTotalStudent());
        return classRepo.saveAndFlush(lop);
    }

    @Override
    public int countByKhoa_KhoaId(Integer khoaId) {
        return classRepo.countByFaculty_FacultyId(khoaId);
    }

    @Override
    public Class findById(Integer classId) {
        return classRepo.findById(classId).orElse(null);
    }

    @Override
    public Class findByName(String className) {
        Class classs = classRepo.findByClassName(className);
        if(classs == null) {
            return null;
        }
        return classs;
    }

    @Override
    @Transactional
    public void deleteLinkUser(AddUsersToClassDTO dto) {
        Class aClass = findById(dto.getClassId());
        Users users = userRepo.getReferenceById(dto.getUserIds().get(0));
        UserClass userClass = userAndClassRepo.findByClassesAndUsers(aClass,users);
        userAndClassRepo.delete(userClass);
    }

    @Override
    @Transactional
    public boolean delete(Integer classId) {
        if(!classRepo.existsById(classId)){
            return false;
        }
        classRepo.deleteById(classId);
        return true;
    }
}
