package com.vn.DATN.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    @Column(name = "courseName")
    private String courseName;
    @Column(name = "duration")
    private String duration;
    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Users users;
}
