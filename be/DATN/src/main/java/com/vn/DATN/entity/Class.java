package com.vn.DATN.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "class")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer classId;
    @Column(name = "className")
    private String className;
    @Column(name = "totalStudent")
    private Integer totalStudent;
    @OneToOne
    @JoinColumn(name = "facultyId", referencedColumnName = "facultyId")
    private Faculty faculty;

    @Column(name = "create_at")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
