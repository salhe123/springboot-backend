package com.example.controller;

import com.example.dto.EmployeeRequest;
import com.example.dto.EmployeeResponse;
import com.example.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.createEmployee(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        EmployeeResponse response = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double fromSalary,
            @RequestParam(required = false) Double toSalary) {
        List<EmployeeResponse> employees = employeeService.getEmployees(name, fromSalary, toSalary);
        return ResponseEntity.ok(employees);
    }
}