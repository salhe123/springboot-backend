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
        employee.setDeleted(false);

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Long id = employeeService.createEmployee(request);

        assertNotNull(id);
        assertEquals(1L, id);
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
        employee.setDeleted(false);

        when(employeeRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(employee));

        EmployeeResponse response = employeeService.getEmployeeById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Mohamed", response.getFirstName());
        assertEquals("Ahmed", response.getLastName());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> employeeService.getEmployeeById(1L));
        assertEquals("Employee not found with id: 1", exception.getMessage());
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
        employee.setDeleted(false);

        when(employeeRepository.findEmployeesByCriteria(null, null, null))
                .thenReturn(Collections.singletonList(employee));

        List<EmployeeResponse> responses = employeeService.getEmployees(null, null, null);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Mohamed", responses.get(0).getFirstName());
        assertEquals("Ahmed", responses.get(0).getLastName());
    }

    @Test
    void testUpdateEmployee() {
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstName("Updated");
        request.setLastName("User");
        request.setDateOfBirth(LocalDate.of(1999, 7, 7));
        request.setSalary(2000.0);
        request.setJoinDate(LocalDate.of(2023, 6, 1));
        request.setDepartment("HR");

        Employee existing = new Employee();
        existing.setId(1L);
        existing.setFirstName("Old");
        existing.setLastName("Name");
        existing.setDateOfBirth(LocalDate.of(1990, 1, 1));
        existing.setSalary(1000.0);
        existing.setJoinDate(LocalDate.of(2023, 1, 1));
        existing.setDepartment("IT");
        existing.setDeleted(false);

        when(employeeRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(existing));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        EmployeeResponse updated = employeeService.updateEmployee(1L, request);

        assertNotNull(updated);
        assertEquals("Updated", updated.getFirstName());
        assertEquals("User", updated.getLastName());
        verify(employeeRepository, times(1)).findByIdAndDeletedFalse(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_NotFound() {
        EmployeeRequest request = new EmployeeRequest();
        when(employeeRepository.findByIdAndDeletedFalse(77L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> employeeService.updateEmployee(77L, request));
        assertEquals("Employee not found with id: 77", exception.getMessage());
        verify(employeeRepository, times(1)).findByIdAndDeletedFalse(77L);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testGetAllEmployees() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("Mohamed");
        employee.setLastName("Ahmed");
        employee.setDateOfBirth(LocalDate.of(2000, 1, 1));
        employee.setSalary(1000.0);
        employee.setJoinDate(LocalDate.of(2023, 5, 18));
        employee.setDepartment("IT");
        employee.setDeleted(false);

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("Sara");
        employee2.setLastName("Ali");
        employee2.setDateOfBirth(LocalDate.of(1995, 2, 2));
        employee2.setSalary(2000.0);
        employee2.setJoinDate(LocalDate.of(2024, 1, 10));
        employee2.setDepartment("HR");
        employee2.setDeleted(false);

        when(employeeRepository.findAllByDeletedFalse()).thenReturn(List.of(employee, employee2));

        List<EmployeeResponse> responses = employeeService.getAllEmployees();

        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("Mohamed", responses.get(0).getFirstName());
        assertEquals("Sara", responses.get(1).getFirstName());
    }

    @Test
    void testDeleteEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setDeleted(false);

        when(employeeRepository.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);

        assertDoesNotThrow(() -> employeeService.deleteEmployee(1L));
        assertTrue(employee.getDeleted());
        verify(employeeRepository, times(1)).findByIdAndDeletedFalse(1L);
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testDeleteEmployee_NotFound() {
        when(employeeRepository.findByIdAndDeletedFalse(99L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> employeeService.deleteEmployee(99L));
        assertEquals("Employee not found with id: 99", exception.getMessage());
        verify(employeeRepository, times(1)).findByIdAndDeletedFalse(99L);
        verify(employeeRepository, never()).save(any(Employee.class));
    }
}