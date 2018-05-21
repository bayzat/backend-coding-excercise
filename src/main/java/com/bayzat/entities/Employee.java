package com.bayzat.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String phoneNumber;
	private String gender;
	private Date dateOfBirth;
	private int salary;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

	@OneToMany(mappedBy = "employee")
	private List<Dependant> dependats;

	public List<Dependant> getDependats() {
		return dependats;
	}

	public void setDependats(List<Dependant> dependats) {
		this.dependats = dependats;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Long getId() {
		return id;
	}

	public Employee() {
		super();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Employee(Company company, String name, String phoneNumber, String gender, Date dateOfBirth, int salary) {
		super();
		this.company = company;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.salary = salary;
	}

}
