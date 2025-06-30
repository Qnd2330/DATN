package com.vn.DATN.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "faculty_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacultyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "facultyId", referencedColumnName = "facultyId")
    private Faculty faculty;
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Users users;
}
