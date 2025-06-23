package com.example.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "employee")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "department")
    private String department;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false; 
}