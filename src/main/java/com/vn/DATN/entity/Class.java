package com.vn.DATN.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "class")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Class extends BaseModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer classID;
    @Column(name = "className")
    private String className;
    @Column(name = "totalStudent")
    private Integer totalStudent;
    @OneToOne
    @JoinColumn(name = "khoaId", referencedColumnName = "khoaId")
    private Khoa khoa;
}
