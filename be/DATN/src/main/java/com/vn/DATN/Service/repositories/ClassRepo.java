package com.vn.DATN.Service.repositories;

import com.vn.DATN.Common.QueryCommon;
import com.vn.DATN.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ClassRepo extends JpaRepository<Class, Integer> {
    @Query(value = QueryCommon.CLASS_DETAIL, nativeQuery = true)
    List<Object[]> findClassDetailByClassId(@Param("classId") Integer classId);
    int countByFaculty_FacultyId(Integer khoaId);

    Class findByClassName (String className);
}
