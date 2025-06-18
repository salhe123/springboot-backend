package com.example.service;

import com.example.dto.EmployeeRequest;
import com.example.dto.EmployeeResponse;
import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEmployee() {
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstName("Mohamed");
        request.setLastName("Ahmed");
        request.setDateOfBirth(LocalDate.of(2000, 1, 1));
        request.setSalary(1000.0);
        request.setJoinDate(LocalDate.of(2023, 5, 18));
        request.setDepartment("IT");

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Mohamed");
        employee.setLastName("Ahmed");
        employee.setDateOfBirth(LocalDate.of(2000, 1, 1));
        employee.setSalary(1000.0);
        employee.setJoinDate(LocalDate.of(2023, 5, 18));
        employee.setDepartment("IT");

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeResponse response = employeeService.createEmployee(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Mohamed", response.getFirstName());
        assertEquals("Ahmed", response.getLastName());
        assertEquals(LocalDate.of(2000, 1, 1), response.getDateOfBirth());
        assertEquals(1000.0, response.getSalary());
        assertEquals(LocalDate.of(2023, 5, 18), response.getJoinDate());
        assertEquals("IT", response.getDepartment());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testGetEmployeeById() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Mohamed");
        employee.setLastName("Ahmed");
        employee.setDateOfBirth(LocalDate.of(2000, 1, 1));
        employee.setSalary(1000.0);
        employee.setJoinDate(LocalDate.of(2023, 5, 18));
        employee.setDepartment("IT");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.getEmployeeById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Mohamed", response.getFirstName());
        assertEquals("Ahmed", response.getLastName());
        assertEquals(LocalDate.of(2000, 1, 1), response.getDateOfBirth());
        assertEquals(1000.0, response.getSalary());
        assertEquals(LocalDate.of(2023, 5, 18), response.getJoinDate());
        assertEquals("IT", response.getDepartment());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEmployees() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Mohamed");
        employee.setLastName("Ahmed");
        employee.setDateOfBirth(LocalDate.of(2000, 1, 1));
        employee.setSalary(1000.0);
        employee.setJoinDate(LocalDate.of(2023, 5, 18));
        employee.setDepartment("IT");

        when(employeeRepository.findEmployeesByCriteria(null, null, null))
                .thenReturn(Collections.singletonList(employee));

        List<EmployeeResponse> responses = employeeService.getEmployees(null, null, null);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Mohamed", responses.get(0).getFirstName());
        assertEquals("Ahmed", responses.get(0).getLastName());
        assertEquals(LocalDate.of(2000, 1, 1), responses.get(0).getDateOfBirth());
        assertEquals(1000.0, responses.get(0).getSalary());
        assertEquals(LocalDate.of(2023, 5, 18), responses.get(0).getJoinDate());
        assertEquals("IT", responses.get(0).getDepartment());
        verify(employeeRepository, times(1)).findEmployeesByCriteria(null, null, null);
    }
}