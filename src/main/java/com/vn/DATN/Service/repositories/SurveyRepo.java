package com.vn.DATN.Service.repositories;

import com.vn.DATN.entity.Question;
import com.vn.DATN.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepo extends JpaRepository<Survey, Integer> {
    Survey findSurveyByTitle(String content);
}
