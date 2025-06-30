package com.vn.DATN.Service;

import com.vn.DATN.DTO.request.UserAnswerDTO;
import com.vn.DATN.entity.UserAnswer;

import java.util.List;

public interface UserAnswerService {
    List<UserAnswer> create(List<UserAnswerDTO> userAnswerDTO);

    UserAnswer get(Integer id);

    boolean delete(Integer id);

}
