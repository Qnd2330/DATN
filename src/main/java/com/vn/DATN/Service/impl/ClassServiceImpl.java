package com.vn.DATN.Service.impl;

import com.vn.DATN.DTO.request.ClassDTO;
import com.vn.DATN.Service.ClassService;
import com.vn.DATN.Service.KhoaService;
import com.vn.DATN.Service.repositories.ClassRepo;
import com.vn.DATN.entity.Class;
import com.vn.DATN.entity.Khoa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ClassRepo classRepo;
    private final KhoaService khoaService;

    @Override
    public Class create(ClassDTO classDTO) {
        Khoa khoa = khoaService.findByKhoaName(classDTO.getKhoaName());
        if(khoa == null) {
            throw new RuntimeException("Không tìm thấy khoa");
        }
        Class lop = Class.builder()
                .className(classDTO.getClassName())
                .totalStudent(classDTO.getTotalStudent()) // Chưa xử lý hàm này
                .khoa(khoa)
                .build();
        return classRepo.save(lop);
    }

    @Override
    public Class edit(ClassDTO classDTO) {
        Class lop = findById(classDTO.getClassID());
        if(lop == null) {
            throw new RuntimeException("Không tìm thấy lớp");
        }
        Khoa khoa = khoaService.findByKhoaName(classDTO.getKhoaName());
        if(khoa == null) {
            throw new RuntimeException("Không tìm thấy khoa");
        }
        lop.setClassName(classDTO.getClassName());
        lop.setKhoa(khoa);
        lop.setTotalStudent(classDTO.getTotalStudent());
        return classRepo.saveAndFlush(lop);
    }

    @Override
    public int countByKhoa_KhoaId(Integer khoaId) {
        return classRepo.countByKhoa_KhoaId(khoaId);
    }

    @Override
    public Class findById(Integer classId) {
        return classRepo.findById(classId).orElse(null);
    }

    @Override
    public Class findByName(String className) {
        Class classs = classRepo.findByClassName(className);
        if(classs == null) {
            return null;
        }
        return classs;
    }

    @Override
    public boolean delete(Integer classId) {
        if(!classRepo.existsById(classId)){
            return false;
        }
        classRepo.deleteById(classId);
        return true;
    }
}
