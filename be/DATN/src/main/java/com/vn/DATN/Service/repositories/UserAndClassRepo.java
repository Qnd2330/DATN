package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.*;
import com.vn.DATN.entity.Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAndClassRepo extends JpaRepository<UserClass, Integer> {
    List<UserClass> findByClasses(Class clazz);
    Page<UserClass> findByClasses_ClassName(String className, Pageable pageable);

    UserClass findByClassesAndUsers(Class classes, Users user);
}