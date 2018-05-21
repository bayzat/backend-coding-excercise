package com.bayzat.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Dependant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String phoneNumber;
	private Date dateOfBirth;
	private String relationToEmployee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "employee_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Employee employee;

	public Long getId() {
		return id;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getRelationToEmployee() {
		return relationToEmployee;
	}

	public void setRelationToEmployee(String relationToEmployee) {
		this.relationToEmployee = relationToEmployee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Dependant() {
		super();
	}

	public Dependant(String name, String phoneNumber, Date dateOfBirth, String relationToEmployee,
			Employee employee) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
		this.relationToEmployee = relationToEmployee;
		this.employee = employee;
	}

}
