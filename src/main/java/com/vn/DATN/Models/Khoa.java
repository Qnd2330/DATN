package com.vn.DATN.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Khoa extends BaseModels {
    @Id
    @Column(name = "khoaId")
    private Integer khoaId;
    @Column(name = "khoaName")
    private String khoaName;
    private int totalClass;
    @OneToMany(mappedBy = "khoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Class> classes;
}
