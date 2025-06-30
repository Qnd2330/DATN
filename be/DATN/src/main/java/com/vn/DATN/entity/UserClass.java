package com.vn.DATN.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_class")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Users users;
    @ManyToOne
    @JoinColumn(name = "classId", referencedColumnName = "classId")
    private Class classes;
}
