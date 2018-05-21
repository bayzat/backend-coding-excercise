package com.bayzat.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bayzat.entities.Company;
import com.bayzat.exceptions.CompanyNotFoundException;
import com.bayzat.repositories.CompanyRepo;

@RestController
public class CompanyController {

	@Autowired
	private CompanyRepo companyRepo;

	@RequestMapping(method = RequestMethod.GET, path = "/companies")
	public List<Company> getAllComapanies() {
		return companyRepo.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, path = "/companies/{id}")
	public Company getCompany(@PathVariable Long id) {
		Optional<Company> company = companyRepo.findById(id);

		if (!company.isPresent()) {
			throw new CompanyNotFoundException("This company is not found ");
		}
		else {
			return (company.get());
		}
	}

	// you can fill just the company name and company address then you can fill
	// the employees later using employees controller
	@RequestMapping(method = RequestMethod.POST, path = "/companies")
	public Company createCompany(@RequestBody Company company) {
		return companyRepo.save(company);
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/companies/{id}")
	public Long deleteCompany(@PathVariable Long id) {
		if (companyRepo.findById(id).isPresent()) {
			companyRepo.deleteById(id);
			return id;
		} else {
			throw new CompanyNotFoundException("This company Id is not exist");
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/companies/{id}")
	public Company updateCompany(@RequestBody Company company, @PathVariable Long id) {
		Optional<Company> companyOpt = companyRepo.findById(id); 
		if (companyOpt.isPresent()) {
			Company currentCompany = companyOpt.get();
			if(company.getAddress()!=null){currentCompany.setAddress(company.getAddress());}
			if(company.getName()!=null){currentCompany.setName(company.getName());}
			return companyRepo.save(currentCompany);

		} else {
			throw new CompanyNotFoundException("This company Id is not exist");
		}
	}

}
