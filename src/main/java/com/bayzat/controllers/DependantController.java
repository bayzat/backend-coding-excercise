package com.bayzat.controllers;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayzat.entities.Company;
import com.bayzat.entities.Dependant;
import com.bayzat.entities.Employee;
import com.bayzat.exceptions.CompanyNotFoundException;
import com.bayzat.exceptions.DependantNotFoundException;
import com.bayzat.exceptions.EmployeeNotFoundException;
import com.bayzat.repositories.CompanyRepo;
import com.bayzat.repositories.DependantRepo;
import com.bayzat.repositories.EmployeeRepo;

@RestController
public class DependantController {

	@Autowired
	DependantRepo dependantRepo;
	@Autowired
	CompanyRepo companyRepo;
	@Autowired
	EmployeeRepo employeeRepo;

	@RequestMapping(method = RequestMethod.GET, path = "/companies/{companyId}/employees/{employeeId}/dependants")
	public Collection<Dependant> getAllDependants(@PathVariable Long companyId, @PathVariable Long employeeId) {
		Company company = validateCompany(companyId);
		validateEmployee(company, employeeId);
		return dependantRepo.findByEmployeeId(employeeId);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/companies/{companyId}/employees/{employeeId}/dependants/{dependantId}")
	public Dependant getDependant(@PathVariable Long companyId, @PathVariable Long employeeId,
			@PathVariable Long dependantId) {

		Company company = validateCompany(companyId);
		validateEmployee(company, employeeId);
		Optional<Dependant> dependant = dependantRepo.findById(dependantId);
		if (dependant.isPresent()) {
			return dependant.get();
		} else {
			throw new DependantNotFoundException("This depentant is not exist id :" + dependantId);
		}
	}

	@RequestMapping(method = RequestMethod.POST, path = "/companies/{companyId}/employees/{employeeId}/dependants")
	public Dependant createDependant(@PathVariable Long companyId, @PathVariable Long employeeId,
			@RequestBody Dependant dependant) {
		Company company = validateCompany(companyId);
		Employee employee = validateEmployee(company, employeeId);
		dependant.setEmployee(employee);
		return dependantRepo.save(dependant);

	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/companies/{companyId}/employees/{employeeId}/dependants/{dependantId}")
	public ResponseEntity<?> deleteDependant(@PathVariable Long companyId, @PathVariable Long employeeId,
			@PathVariable Long dependantId) {

		Company company = validateCompany(companyId);
		Employee employee = validateEmployee(company, employeeId);
		Optional<Dependant> dependant = dependantRepo.findById(dependantId);
		if (dependant.isPresent() && employee.getDependats().contains(dependant.get())) {
			dependantRepo.delete((dependant.get()));
			return ResponseEntity.ok().build();
		} else {
			throw new DependantNotFoundException("This depentant is not exist id :" + dependantId);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/companies/{companyId}/employees/{employeeId}/dependants/{dependantId}")
	public Dependant updateDependant(@PathVariable Long companyId, @PathVariable Long employeeId,
			@PathVariable Long dependantId,
			@RequestBody Dependant dependant) {
		Company company = validateCompany(companyId);
		Employee employee = validateEmployee(company, employeeId);
		Optional<Dependant> dependantOpt = dependantRepo.findById(dependantId);
		if (dependantOpt.isPresent() && employee.getDependats().contains(dependantOpt.get())) {
			Dependant dependatObj = dependantOpt.get();
			dependatObj.setDateOfBirth(dependant.getDateOfBirth());
			// dependatObj.setEmployee(dependant.getEmployee());
			dependatObj.setName(dependant.getName());
			dependatObj.setPhoneNumber(dependant.getPhoneNumber());
			dependatObj.setRelationToEmployee(dependant.getRelationToEmployee());
			return dependantRepo.save(dependatObj);
			
		} else {
			throw new DependantNotFoundException("This depentant is not exist id :" + dependantId);
		}
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
		if (employee.isPresent()&& company.getEmployees().contains(employee.get())) {
			return employee.get();
		} else {
			throw new EmployeeNotFoundException(
					"This employee is not exist id :" + employeeId + " In company ID :" + company.getId());
		}

	}
	
}
