package com.vn.DATN.Service.repositories;

import com.vn.DATN.Common.QueryCommon;
import com.vn.DATN.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Integer> {
    Optional<Users> findByUserName(String userName);

    @Query("SELECT u FROM Users u WHERE u.deleted = false")
    Page<Users> findAllNotDeleted(Pageable pageable);

    @Query(value = QueryCommon.GET_ALL_STUDENT, nativeQuery = true)
    List<Users> findUnassignedStudents();
}
