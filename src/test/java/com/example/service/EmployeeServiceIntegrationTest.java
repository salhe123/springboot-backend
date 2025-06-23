package com.example.service;

import com.example.dto.EmployeeRequest;
import com.example.dto.EmployeeResponse;
import com.example.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
public class EmployeeServiceIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("employee_db")
            .withUsername("postgres")
            .withPassword("123456");

    @DynamicPropertySource
    static void overrideDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void cleanUp() {
        employeeRepository.deleteAll();
    }

    @Test
    void testCreateAndGetEmployee() {
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setDateOfBirth(LocalDate.of(1992, 8, 15));
        request.setSalary(3000.0);
        request.setJoinDate(LocalDate.of(2023, 5, 10));
        request.setDepartment("Engineering");

        Long id = employeeService.createEmployee(request);
        assertThat(id).isNotNull();

        EmployeeResponse response = employeeService.getEmployeeById(id);

        assertThat(response.getFirstName()).isEqualTo("John"); // FIXED
        assertThat(response.getLastName()).isEqualTo("Doe");
        assertThat(response.getDepartment()).isEqualTo("Engineering");
    }

    @Test
    void testUpdateEmployee() {
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstName("Jane");
        request.setLastName("Smith");
        request.setDateOfBirth(LocalDate.of(1990, 5, 5));
        request.setSalary(2500.0);
        request.setJoinDate(LocalDate.of(2022, 6, 15));
        request.setDepartment("IT");

        Long id = employeeService.createEmployee(request);

        EmployeeRequest updateRequest = new EmployeeRequest();
        updateRequest.setFirstName("JaneUpdated");
        updateRequest.setLastName("SmithUpdated");
        updateRequest.setDateOfBirth(LocalDate.of(1991, 1, 1));
        updateRequest.setSalary(3500.0);
        updateRequest.setJoinDate(LocalDate.of(2024, 6, 23));
        updateRequest.setDepartment("HR");

        EmployeeResponse updated = employeeService.updateEmployee(id, updateRequest);

        assertThat(updated.getFirstName()).isEqualTo("JaneUpdated");
        assertThat(updated.getSalary()).isEqualTo(3500.0);
        assertThat(updated.getDepartment()).isEqualTo("HR");
    }

    @Test
    void testGetAllEmployees() {
        EmployeeRequest req1 = new EmployeeRequest();
        req1.setFirstName("Alice");
        req1.setLastName("Wonder");
        req1.setDateOfBirth(LocalDate.of(1995, 2, 2));
        req1.setSalary(2000.0);
        req1.setJoinDate(LocalDate.of(2024, 1, 10));
        req1.setDepartment("HR");

        EmployeeRequest req2 = new EmployeeRequest();
        req2.setFirstName("Bob");
        req2.setLastName("Builder");
        req2.setDateOfBirth(LocalDate.of(1985, 7, 7));
        req2.setSalary(4000.0);
        req2.setJoinDate(LocalDate.of(2023, 3, 5));
        req2.setDepartment("Engineering");

        employeeService.createEmployee(req1);
        employeeService.createEmployee(req2);

        List<EmployeeResponse> all = employeeService.getAllEmployees();
        assertThat(all).hasSize(2);
        assertThat(all.stream().map(EmployeeResponse::getFirstName))
                .containsExactlyInAnyOrder("Alice", "Bob");
    }

    @Test
    void testDeleteEmployee() {
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstName("Delete");
        request.setLastName("Me");
        request.setDateOfBirth(LocalDate.of(1998, 4, 12));
        request.setSalary(1500.0);
        request.setJoinDate(LocalDate.of(2022, 10, 1));
        request.setDepartment("Support");

        Long id = employeeService.createEmployee(request);

        employeeService.deleteEmployee(id);

        assertThat(employeeRepository.findById(id)).isNotPresent();
    }
}