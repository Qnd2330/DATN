package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.Faculty;
import com.vn.DATN.entity.FacultyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyAndUserRepo extends JpaRepository<FacultyUser, Integer> {
    FacultyUser findByFaculty (Faculty faculty);
}
