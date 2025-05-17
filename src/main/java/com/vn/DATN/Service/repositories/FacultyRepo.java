package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepo extends JpaRepository<Faculty,Integer> {
    Faculty findByFacultyName (String facultyName);
}
