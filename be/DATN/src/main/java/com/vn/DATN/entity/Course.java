package com.vn.DATN.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "course")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    @Column(name = "courseName")
    private String courseName;
    @ManyToOne
    @JoinColumn(name = "teach_id", referencedColumnName = "userId")
    private Users users;
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
