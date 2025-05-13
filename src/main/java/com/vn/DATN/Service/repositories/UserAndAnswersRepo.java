package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAndAnswersRepo extends JpaRepository<UserAnswer, Integer> {
}
