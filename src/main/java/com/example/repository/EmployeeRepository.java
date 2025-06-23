package com.example.repository;

import com.example.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
       @Query("SELECT e FROM Employee e WHERE " +
       "e.deleted = false AND " +
       "(:name IS NULL OR LOWER(e.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
       "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
       "(:fromSalary IS NULL OR e.salary >= :fromSalary) AND " +
       "(:toSalary IS NULL OR e.salary <= :toSalary)")
List<Employee> findEmployeesByCriteria(@Param("name") String name,
                                       @Param("fromSalary") Double fromSalary,
                                       @Param("toSalary") Double toSalary);

List<Employee> findAllByDeletedFalse();

Optional<Employee> findByIdAndDeletedFalse(Long id);
}