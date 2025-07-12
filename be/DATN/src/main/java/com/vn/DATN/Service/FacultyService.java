package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.FacultyDTO;
import com.vn.DATN.DTO.request.FacultyWithManagerDTO;
import com.vn.DATN.entity.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FacultyService {
    Page<FacultyWithManagerDTO> list(Pageable pageable);

    List<Faculty> getAll();
    
    FacultyWithManagerDTO getById(Integer id);
    
    Faculty create(FacultyDTO facultyDTO);

    Faculty edit(FacultyDTO facultyDTO);
    Faculty findByFacultyName(String facultyName);

    Faculty findById(Integer facultyId);
}
