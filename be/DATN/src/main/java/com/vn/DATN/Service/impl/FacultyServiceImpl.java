package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.FacultyDTO;
import com.vn.DATN.DTO.request.FacultyWithManagerDTO;
import com.vn.DATN.Service.FacultyService;
import com.vn.DATN.Service.repositories.FacultyAndUserRepo;
import com.vn.DATN.Service.repositories.FacultyRepo;
import com.vn.DATN.Service.repositories.UserRepo;
import com.vn.DATN.entity.Faculty;
import com.vn.DATN.entity.FacultyUser;
import com.vn.DATN.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepo facultyRepo;

    private final UserRepo userRepo;

    private final FacultyAndUserRepo facultyAndUserRepo;

    @Override
    public Page<FacultyWithManagerDTO> list(Pageable pageable) {
        return facultyRepo.getFacultyWithManagerAndDates(pageable);
    }

    @Override
    public List<Faculty> getAll() {
        return facultyRepo.findAll();
    }

    @Override
    public FacultyWithManagerDTO getById(Integer id) {
        return facultyRepo.getFacultyWithManagerById(id);
    }

    @Override
    public Faculty create(FacultyDTO facultyDTO) {
        Users users =userRepo.findById(facultyDTO.getUserId()).orElse(null);
        if(users == null) {
            throw new RuntimeException("Không tìm thấy giáo viên");
        }
        Faculty faculty = Faculty.builder()
                .facultyName(facultyDTO.getFacultyName())
                .build();
        FacultyUser facultyUser = FacultyUser.builder()
                .faculty(faculty)
                .users(users)
                .build();
        facultyRepo.save(faculty);
        facultyAndUserRepo.save(facultyUser);
        return faculty;
    }

    @Override
    public Faculty edit(FacultyDTO facultyDTO) {
        Users users =userRepo.findById(facultyDTO.getUserId()).orElse(null);
        if(users == null) {
            throw new RuntimeException("Không tìm thấy giáo viên");
        }
        Faculty faculty = findById(facultyDTO.getFacultyId());
        if(faculty == null){
            throw new RuntimeException("Không tìm thấy Faculty");
        }
        FacultyUser facultyUser = facultyAndUserRepo.findByFaculty(faculty);
        facultyUser.setUsers(users);
        faculty.setFacultyName(facultyDTO.getFacultyName());
        facultyRepo.saveAndFlush(faculty);
        facultyAndUserRepo.saveAndFlush(facultyUser);
        return faculty ;
    }

    @Override
    public Faculty findByFacultyName(String facultyName) {
        Faculty faculty = facultyRepo.findByFacultyName(facultyName);
        if(faculty == null) {
            return null;
        }
        return faculty;
    }

    @Override
    public Faculty findById(Integer facultyId) {
        return facultyRepo.findById(facultyId).orElse(null);
    }
}
