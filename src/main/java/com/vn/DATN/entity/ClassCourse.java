package com.vn.DATN.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "class_course")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "courseId")
    private Course course;
    @ManyToOne
    @JoinColumn(name = "classId", referencedColumnName = "classId")
    private Class classes;
}

