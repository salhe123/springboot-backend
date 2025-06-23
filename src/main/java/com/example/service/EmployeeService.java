package com.example.service;

import com.example.dto.EmployeeRequest;
import com.example.dto.EmployeeResponse;
import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Long createEmployee(EmployeeRequest request) {
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setSalary(request.getSalary());
        employee.setJoinDate(request.getJoinDate());
        employee.setDepartment(request.getDepartment());
        employee.setDeleted(false); // default to not deleted
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee.getId();
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        return mapToResponse(employee);
    }

    public List<EmployeeResponse> getEmployees(String name, Double fromSalary, Double toSalary) {
        List<Employee> employees = employeeRepository.findEmployeesByCriteria(name, fromSalary, toSalary);
        return employees.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAllByDeletedFalse();
        return employees.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = employeeRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setSalary(request.getSalary());
        employee.setJoinDate(request.getJoinDate());
        employee.setDepartment(request.getDepartment());
        Employee updated = employeeRepository.save(employee);
        return mapToResponse(updated);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        employee.setDeleted(true);
        employeeRepository.save(employee);
    }

    private EmployeeResponse mapToResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setFirstName(employee.getFirstName());
        response.setLastName(employee.getLastName());
        response.setDateOfBirth(employee.getDateOfBirth());
        response.setSalary(employee.getSalary());
        response.setJoinDate(employee.getJoinDate());
        response.setDepartment(employee.getDepartment());
        return response;
    }
}