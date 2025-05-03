package com.vn.DATN.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "survey")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Survey extends BaseModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer surveyId;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "score")
    private Integer score;
    @OneToOne
    @JoinColumn(name = "courseId")
    private Course course;
}
