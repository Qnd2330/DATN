package com.vn.DATN.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "survey")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Survey{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer surveyId;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "deadline")
    private LocalDate deadline;
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
