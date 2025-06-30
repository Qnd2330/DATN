package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.FacultyDTO;
import com.vn.DATN.Service.FacultyService;
import com.vn.DATN.Service.repositories.FacultyRepo;
import com.vn.DATN.entity.Faculty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepo facultyRepo;

    @Override
    public Page<Faculty> list(Pageable pageable) {
        return facultyRepo.findAll(pageable);
    }

    @Override
    public List<Faculty> getAll() {
        return facultyRepo.findAll();
    }

    @Override
    public Faculty create(FacultyDTO facultyDTO) {
        Faculty faculty = Faculty.builder()
                .facultyName(facultyDTO.getFacultyName())
                .build();
        return facultyRepo.save(faculty);
    }

    @Override
    public Faculty edit(FacultyDTO facultyDTO) {
        Faculty faculty = findById(facultyDTO.getFacultyId());
        if(faculty == null){
            throw new RuntimeException("Không tìm thấy Faculty");
        }
        faculty.setFacultyName(facultyDTO.getFacultyName());
        return facultyRepo.saveAndFlush(faculty);
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
