package com.vn.DATN.DTO.mapper;

import com.vn.DATN.DTO.request.ClassDTO;
import com.vn.DATN.entity.Class;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClassMapper {
    ClassMapper INSTANCE = Mappers.getMapper(ClassMapper.class);

    ClassDTO toDTO(Class classs);
    Class toEntity(ClassDTO classDTO);
}
