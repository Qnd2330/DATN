package com.vn.DATN.entity;

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
    @JoinColumn(name = "khoaId", referencedColumnName = "khoaId")
    private Khoa khoa;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "updated_at")
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
