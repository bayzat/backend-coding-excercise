package com.bayzat.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bayzat.entities.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
	Collection<Employee> findByCompanyId(Long companyId);

}
