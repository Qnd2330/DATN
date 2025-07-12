package com.vn.DATN.DTO.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public interface FacultyWithManagerDTO {
    Integer getFacultyId();
    String getFacultyName();
    Integer getUserId();
    String getManagerName();
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDateTime getCreateAt();
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDateTime getUpdatedAt();
}
