package com.vn.DATN.DTO.mapper;

import com.vn.DATN.DTO.request.KhoaDTO;
import com.vn.DATN.entity.Khoa;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface KhoaMapper {
    KhoaMapper INSTANCE = Mappers.getMapper(KhoaMapper.class);

    KhoaDTO toDTO(Khoa khoa);
}
