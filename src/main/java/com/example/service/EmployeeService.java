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

    // Changed return type to Long
    public Long createEmployee(EmployeeRequest request) {
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setSalary(request.getSalary());
        employee.setJoinDate(request.getJoinDate());
        employee.setDepartment(request.getDepartment());
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee.getId();
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    public List<EmployeeResponse> getEmployees(String name, Double fromSalary, Double toSalary) {
        List<Employee> employees = employeeRepository.findEmployeesByCriteria(name, fromSalary, toSalary);
        return employees.stream().map(this::mapToResponse).collect(Collectors.toList());
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

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
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
    public List<EmployeeResponse> getAllEmployees() {
    List<Employee> employees = employeeRepository.findAll();
    return employees.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
}

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }
}