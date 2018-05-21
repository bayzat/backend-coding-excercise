package com.bayzat.controllers;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayzat.entities.Company;
import com.bayzat.entities.Employee;
import com.bayzat.exceptions.CompanyNotFoundException;
import com.bayzat.exceptions.EmployeeNotFoundException;
import com.bayzat.repositories.CompanyRepo;
import com.bayzat.repositories.EmployeeRepo;

@RestController
public class EmployeeController {

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	EmployeeRepo employeeRepo;

	@RequestMapping(method = RequestMethod.GET, path = "/companies/{companyId}/employees")
	public Collection<Employee> getAllEmployees(@PathVariable Long companyId) {
		if (companyRepo.existsById(companyId)) {
			return employeeRepo.findByCompanyId(companyId);
		} else {
			throw new CompanyNotFoundException("This company is not exist id : " + companyId);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/companies/{companyId}/employees/{employeeId}")
	public Employee getEmployee(@PathVariable Long companyId, @PathVariable Long employeeId) {
		Company company = validateCompany(companyId);
		return validateEmployee(company, employeeId);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/companies/{companyId}/employees")
	public Employee createEmployee(@PathVariable Long companyId, @RequestBody Employee employee) {
		Company company = validateCompany(companyId);
		employee.setCompany(company);
		return employeeRepo.save(employee);
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/companies/{companyId}/employees/{employeeId}")
	public void deleteEmployee(@PathVariable Long companyId, @PathVariable Long employeeId) {
		Company company = validateCompany(companyId);
		Employee employee = validateEmployee(company, employeeId);
		employeeRepo.delete(employee);
		// return ResponseEntity.ok().build();
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/companies/{companyId}/employees/{employeeId}")
	public Employee updateEmployee(@PathVariable Long companyId, @PathVariable Long employeeId,
			@RequestBody Employee employee) {
		Company company = validateCompany(companyId);
		Employee currentEmployee = validateEmployee(company, employeeId);
		currentEmployee.setDateOfBirth(employee.getDateOfBirth());
		currentEmployee.setGender(employee.getGender());
		currentEmployee.setName(employee.getName());
		currentEmployee.setPhoneNumber(employee.getPhoneNumber());
		currentEmployee.setSalary(employee.getSalary());
		return employeeRepo.save(currentEmployee);
	}

	private Company validateCompany(Long id) {

		Optional<Company> company = companyRepo.findById(id);

		if (company.isPresent()) {
			return company.get();
		} else {
			throw new CompanyNotFoundException("This company is not exist id :" + id);
		}

	}

	private Employee validateEmployee(Company company, Long employeeId) {

		Optional<Employee> employee = employeeRepo.findById(employeeId);
		if (employee.isPresent() && company.getEmployees().contains(employee.get())) {
			return employee.get();
		} else {
			throw new EmployeeNotFoundException(
					"This employee is not exist id :" + employeeId + " In company ID :" + company.getId());
		}

	}

}
