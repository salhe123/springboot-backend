package com.example.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRequest {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Double salary;
    private LocalDate joinDate;
    private String department;
}