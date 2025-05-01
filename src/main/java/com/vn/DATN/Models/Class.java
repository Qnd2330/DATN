package com.vn.DATN.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "class")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Class extends BaseModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer classID;
    @Column(name = "className")
    private String className;
    @Column(name = "totalStudent")
    private Integer totalStudent;
    @ManyToOne
    @JoinColumn(name = "khoaId", referencedColumnName = "khoaId")
    private Khoa khoa;
}
