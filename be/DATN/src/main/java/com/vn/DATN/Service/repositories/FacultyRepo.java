package com.vn.DATN.Service.repositories;

import com.vn.DATN.Common.QueryCommon;
import com.vn.DATN.DTO.request.FacultyWithManagerDTO;
import com.vn.DATN.entity.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FacultyRepo extends JpaRepository<Faculty,Integer> {
    Faculty findByFacultyName (String facultyName);
    
    @Query(value = QueryCommon.GET_LIST_FACULTY, nativeQuery = true)
    Page<FacultyWithManagerDTO> getFacultyWithManagerAndDates(Pageable pageable);
    
    @Query(value =QueryCommon.GET_DETAIL_FACULTY, nativeQuery = true)
    FacultyWithManagerDTO getFacultyWithManagerById(@Param("id") Integer id);
}
