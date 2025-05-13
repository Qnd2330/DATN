package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.UserAnswerDTO;
import com.vn.DATN.Service.QuestionAndAnswerService;
import com.vn.DATN.Service.UserAnswerService;
import com.vn.DATN.Service.repositories.UserAndAnswersRepo;
import com.vn.DATN.entity.UserAnswer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserAnswerServiceImpl implements UserAnswerService {

    private final UserAndAnswersRepo userAndAnswersRepo;
    private final QuestionAndAnswerService questionAndAnswerService;

    @Override
    public List<UserAnswer> create(List<UserAnswerDTO> userAnswerDTO) {
     return null;
    }

    @Override
    public UserAnswer get(Integer id) {
        return userAndAnswersRepo.findById(id).orElse(null);
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
