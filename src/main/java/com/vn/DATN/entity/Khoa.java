package com.vn.DATN.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Khoa extends BaseModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer khoaId;
    @Column(name = "khoaName")
    private String khoaName;
}
